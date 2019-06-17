package io.axoniq.training.labs.giftcard.coreapi;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class CardSummary {

    @Id
    private String cardId;

    private int initialValue;
    private Instant issuedAt;
    private int remainingValue;

    public CardSummary() {
    }

    public CardSummary(String cardId, int initialValue) {
        this.cardId = cardId;
        this.initialValue = initialValue;
        this.remainingValue = initialValue;
        this.issuedAt = Instant.now();
    }

    public String getCardId() {
        return cardId;
    }

    public int getInitialValue() {
        return initialValue;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public int getRemainingValue() {
        return remainingValue;
    }

    public void reimburse(int amount) {
        this.remainingValue += amount;
    }

    public void redeem(int amount) {
        this.remainingValue -= amount;
    }
}
