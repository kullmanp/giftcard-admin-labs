package io.axoniq.training.labs.giftcard.coreapi;

import java.time.Instant;

public class CardSummary {

    public String getCardId() {
        return null;
    }

    public int getInitialValue() {
        return 0;
    }

    public Instant getIssuedAt() {
        return Instant.now();
    }

    public int getRemainingValue() {
        return 0;
    }
}
