package io.axoniq.training.labs.giftcard.command;

import io.axoniq.training.labs.giftcard.coreapi.CardIssuedEvent;
import io.axoniq.training.labs.giftcard.coreapi.CardRedeemedEvent;
import io.axoniq.training.labs.giftcard.coreapi.IssueCardCommand;
import io.axoniq.training.labs.giftcard.coreapi.RedeemCardCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class GiftCard {

    @AggregateIdentifier
    private String cardId;

    private int amountAvailable;

    @EventSourcingHandler
    public void on(CardIssuedEvent e) {
        this.cardId = e.getCardId();
        this.amountAvailable = e.getAmount();
    }

    @EventSourcingHandler
    public void on(CardRedeemedEvent e) {
        this.amountAvailable -= e.getAmount();
    }


    public GiftCard() {
    }

    @CommandHandler
    public GiftCard(IssueCardCommand e) {
        apply(new CardIssuedEvent(e.getCardId(), e.getAmount()));
    }

    @CommandHandler
    public void handle(RedeemCardCommand e) {
        if (amountAvailable < e.getAmount()) {
            throw new IllegalStateException("Amount is too big");
        }
        if (e.getAmount() <= 0) {
            throw new IllegalArgumentException("bla");
        }
        apply(new CardRedeemedEvent(e.getCardId(), e.getTransactionId(), e.getAmount()));
    }
}
