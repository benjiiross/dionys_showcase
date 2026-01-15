package com.benjiiross.dionys.games.purple.usecase

import com.benjiiross.dionys.games.purple.domain.Card
import com.benjiiross.dionys.games.purple.domain.PurpleCardRepository

class GeneratePurpleResult(private val purpleCardRepository: PurpleCardRepository) {

    operator fun invoke(): Card {
        return purpleCardRepository.drawCard()
    }

    fun resetDeck() {
        purpleCardRepository.resetDeck()
    }

    fun getRemainingCards(): Int {
        return purpleCardRepository.getRemainingCardsCount()
    }
}
