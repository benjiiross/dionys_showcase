package com.benjiiross.dionys.games.purple.domain

interface PurpleCardRepository {
    fun drawCard(): Card

    fun resetDeck()

    fun getRemainingCardsCount(): Int
}
