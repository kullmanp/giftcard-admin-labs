package io.axoniq.training.labs.giftcard.saga;

import io.axoniq.training.labs.giftcard.coreapi.CardRedeemedEvent;
import io.axoniq.training.labs.giftcard.coreapi.RedeemCardCommand;
import io.axoniq.training.labs.order.coreapi.OrderPlacedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
public class GiftCardPaymentSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void start(OrderPlacedEvent event) {
        SagaLifecycle.associateWith("cardId", event.getCardId());
        commandGateway.send(new RedeemCardCommand(event.getCardId(), UUID.randomUUID().toString(), event.getGiftCardAmount()));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "cardId")
    public void cardRedeemed(CardRedeemedEvent event) {

    }

}
