package io.axoniq.training.labs.giftcard.coreapi

data class FindCardSummariesQuery(val offset: Int, val limit: Int)

class CountCardSummariesQuery {
    override fun toString(): String = "CountCardSummariesQuery"
}
