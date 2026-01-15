package com.benjiiross.dionys.games.purple.infrastructure

import com.benjiiross.dionys.games.purple.domain.Card
import com.benjiiross.dionys.games.purple.domain.PurpleCardRepository
import com.benjiiross.dionys.games.purple.domain.Rank
import com.benjiiross.dionys.games.purple.domain.Suit

class PurpleCardsInMemory : PurpleCardRepository {

    private val allCards: List<Card> = buildStandardDeck()
    private var remainingCards: MutableList<Card> = allCards.shuffled().toMutableList()

    private fun buildStandardDeck(): List<Card> {
        val deck = mutableListOf<Card>()
        for (suit in Suit.entries) {
            for (rank in Rank.entries) {
                deck.add(Card(rank, suit))
            }
        }
        return deck
    }

    override fun drawCard(): Card {
        if (remainingCards.isEmpty()) {
            remainingCards = allCards.shuffled().toMutableList()
        }
        return remainingCards.removeFirst()
    }

    override fun resetDeck() {
        remainingCards = allCards.shuffled().toMutableList()
    }

    override fun getRemainingCardsCount(): Int {
        return remainingCards.size
    }
}
