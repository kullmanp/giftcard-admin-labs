package io.axoniq.training.labs.giftcard.query;

import io.axoniq.training.labs.giftcard.coreapi.CardSummary;
import io.axoniq.training.labs.giftcard.coreapi.CountCardSummariesQuery;
import io.axoniq.training.labs.giftcard.coreapi.FindCardSummariesQuery;
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
        return Collections.emptyList();
    }

    @QueryHandler
    public Long handle(CountCardSummariesQuery query) {
        return 0L;
    }
}
