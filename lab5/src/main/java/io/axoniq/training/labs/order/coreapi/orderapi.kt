package io.axoniq.training.labs.order.coreapi

import org.axonframework.commandhandling.RoutingKey

data class OrderPlacedEvent(val orderId: String, val cardId: String, val giftCardAmount: Int)
data class ConfirmGiftCardPaymentCommand(@RoutingKey val orderId: String, val cardId: String, val giftCardAmount: Int)
data class RejectGiftCardPaymentCommand(@RoutingKey val orderId: String, val cardId: String)
