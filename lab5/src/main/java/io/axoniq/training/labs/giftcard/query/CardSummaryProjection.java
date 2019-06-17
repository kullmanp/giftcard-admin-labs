package io.axoniq.training.labs.giftcard.query;

import io.axoniq.training.labs.giftcard.coreapi.*;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.List;

@Profile("query")
@Component
public class CardSummaryProjection {

    private final EntityManager entityManager;

    public CardSummaryProjection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @EventHandler
    public void on(CardIssuedEvent evt, @Timestamp Instant instant) {
        entityManager.persist(new CardSummary(evt.getCardId(), evt.getAmount(), instant));
    }

    @EventHandler
    public void on(CardRedeemedEvent evt) {
        CardSummary summary = entityManager.find(CardSummary.class, evt.getCardId());
        summary.setRemainingValue(summary.getRemainingValue() - evt.getAmount());
    }

    @EventHandler
    public void on(CardReimbursedEvent evt) {
        CardSummary summary = entityManager.find(CardSummary.class, evt.getCardId());
        summary.setRemainingValue(summary.getRemainingValue() + evt.getAmount());
    }

    @QueryHandler
    public List<CardSummary> handle(FindCardSummariesQuery query) {
        return entityManager.createQuery("SELECT c FROM CardSummary c ORDER BY c.cardId", CardSummary.class)
                            .setFirstResult(query.getOffset())
                            .setMaxResults(query.getLimit()).getResultList();
    }

    @QueryHandler
    public Long handle(CountCardSummariesQuery query) {
        return entityManager.createQuery("SELECT COUNT(c) FROM CardSummary c", Long.class)
                            .getSingleResult();
    }
}
