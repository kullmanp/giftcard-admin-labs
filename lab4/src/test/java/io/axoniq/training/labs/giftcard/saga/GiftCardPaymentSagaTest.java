package io.axoniq.training.labs.giftcard.saga;

import io.axoniq.training.labs.giftcard.coreapi.CardIssuedEvent;
import io.axoniq.training.labs.giftcard.coreapi.RedeemCardCommand;
import io.axoniq.training.labs.order.coreapi.OrderPlacedEvent;
import org.axonframework.test.saga.SagaTestFixture;
import org.junit.*;

import static org.junit.Assert.*;

public class GiftCardPaymentSagaTest {

    private SagaTestFixture<GiftCardPaymentSaga> fixture;

    @Before
    public void setUp() {
        this.fixture = new SagaTestFixture<>(GiftCardPaymentSaga.class);
    }

    @Test
    public void shouldRedeemGiftCardWhenOrderIsPlaced() {
        fixture.givenAPublished(new CardIssuedEvent("id", 100))
                .whenPublishingA(new OrderPlacedEvent("oid", "id", 12))
                .expectDispatchedCommands(new RedeemCardCommand("id", "tid", 12));

    }
}
