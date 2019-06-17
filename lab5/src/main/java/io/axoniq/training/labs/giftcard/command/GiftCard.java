package io.axoniq.training.labs.giftcard.command;

import io.axoniq.training.labs.giftcard.coreapi.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Profile("command")
@Aggregate
public class GiftCard {

    private static final Logger logger = LoggerFactory.getLogger(GiftCard.class);

    @AggregateIdentifier
    private String id;

    @AggregateMember
    private List<GiftCardTransaction> transactions = new ArrayList<>();

    private int remainingValue;

    public GiftCard() {
    }

    @CommandHandler
    public GiftCard(IssueCardCommand cmd) {
        if (cmd.getAmount() <= 0) {
            throw new IllegalArgumentException("amount <= 0");
        }
        logger.info("Handled IssueCardCommand {}", cmd.getCardId());
        apply(new CardIssuedEvent(cmd.getCardId(), cmd.getAmount()));
    }

    @CommandHandler
    public void handle(RedeemCardCommand cmd) {
        if (cmd.getAmount() <= 0) {
            throw new IllegalArgumentException("amount <= 0");
        }
        if (cmd.getAmount() > remainingValue) {
            throw new IllegalStateException("amount > remaining value");
        }
        if (transactions.stream().map(GiftCardTransaction::getTransactionId).anyMatch(cmd.getTransactionId()::equals)) {
            throw new IllegalStateException("TransactionId must be unique");
        }
        logger.info("Handled RedeemCardCommand {}", cmd.getCardId());
        apply(new CardRedeemedEvent(id, cmd.getTransactionId(), cmd.getAmount()));
    }

    @EventSourcingHandler
    public void on(CardIssuedEvent evt) {
        id = evt.getCardId();
        remainingValue = evt.getAmount();
    }

    @EventSourcingHandler
    protected void on(CardReimbursedEvent event) {
        this.remainingValue += event.getAmount();
    }

    @EventSourcingHandler
    public void on(CardRedeemedEvent evt) {
        remainingValue -= evt.getAmount();
        transactions.add(new GiftCardTransaction(evt.getTransactionId(), evt.getAmount()));
    }
}
