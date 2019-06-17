package io.axoniq.training.labs.giftcard.command;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class GiftCardTest {

    private FixtureConfiguration<GiftCard> fixture;

    @Before
    public void setup() {
        fixture = new AggregateTestFixture<>(GiftCard.class);
    }

    @Test
    public void shouldIssueGiftCard() {
        fail("To be implemented");
    }

    @Test
    public void shouldRedeemGiftCard() {
        fail("To be implemented");
    }

    @Test
    public void shouldNotRedeemWithNegativeAmount() {
        fail("To be implemented");
    }

    @Test
    public void shouldNotRedeemWhenThereIsNotEnoughMoney() {
        fail("To be implemented");
    }
}
