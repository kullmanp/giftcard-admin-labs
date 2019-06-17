package io.axoniq.training.labs.giftcard.query;

import io.axoniq.training.labs.giftcard.command.GiftCardTransaction;
import io.axoniq.training.labs.giftcard.coreapi.*;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Component
public class CardSummaryProjection {

    private static final Logger logger = LoggerFactory.getLogger(CardSummaryProjection.class);
    private final EntityManager entityManager;

    public CardSummaryProjection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @QueryHandler
    public List<CardSummary> handle(FindCardSummariesQuery query) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return findAll().subList(query.getOffset(), query.getOffset() + query.getLimit());
    }

    private List<CardSummary> findAll() {
        return entityManager.createQuery("select s from CardSummary s", CardSummary.class).getResultList();
    }

    @QueryHandler
    public Long handle(CountCardSummariesQuery query) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return Long.valueOf(findAll().size());
    }

    @EventHandler
    public void cardIssued(CardIssuedEvent evt) {
        CardSummary cardSummary = new CardSummary(evt.getCardId(), evt.getAmount());
        entityManager.persist(cardSummary);
    }

    @EventHandler
    protected void on(CardReimbursedEvent event) {
        CardSummary cardSummary = entityManager.find(CardSummary.class, event.getCardId());
        cardSummary.reimburse(event.getAmount());
    }

    @EventHandler
    public void on(CardRedeemedEvent evt) {
        CardSummary cardSummary = entityManager.find(CardSummary.class, evt.getCardId());
        cardSummary.redeem(evt.getAmount());
    }

}
