package io.axoniq.training.labs.giftcard.coreapi

data class CardIssuedEvent(val cardId: String, val amount: Int)
data class CardRedeemedEvent(val cardId: String, val transactionId : String, val amount: Int)
data class CardReimbursedEvent(val cardId: String, val transactionId : String, val amount: Int)
