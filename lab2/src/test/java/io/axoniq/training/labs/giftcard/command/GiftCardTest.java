package io.axoniq.training.labs.giftcard.command;

import io.axoniq.training.labs.giftcard.coreapi.CardIssuedEvent;
import io.axoniq.training.labs.giftcard.coreapi.CardRedeemedEvent;
import io.axoniq.training.labs.giftcard.coreapi.IssueCardCommand;
import io.axoniq.training.labs.giftcard.coreapi.RedeemCardCommand;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class GiftCardTest {

    private FixtureConfiguration<GiftCard> fixture;

    @Before
    public void setup() {
        fixture = new AggregateTestFixture<>(GiftCard.class);
    }

    @Test
    public void shouldIssueGiftCard() {
        fixture.givenNoPriorActivity()
                .when(new IssueCardCommand("id", 12))
                .expectEvents(new CardIssuedEvent("id", 12));
    }

    @Test
    public void shouldRedeemGiftCard() {
        fixture.given(new CardIssuedEvent("id", 100))
                .when(new RedeemCardCommand("id", "trans", 66))
                .expectEvents(new CardRedeemedEvent("id", "trans", 66));
    }

    @Test
    public void shouldNotRedeemWithNegativeAmount() {
        fixture.given(new CardIssuedEvent("id", 100))
                .when(new RedeemCardCommand("id", "trans", -12))
                .expectException(IllegalArgumentException.class);
    }

    @Test
    public void shouldNotRedeemWhenThereIsNotEnoughMoney() {
        fixture.given(new CardIssuedEvent("id", 100), new CardRedeemedEvent("id", "t1", 66))
                .when(new RedeemCardCommand("id", "t2", 50))
                .expectException(IllegalStateException.class);

    }
}
