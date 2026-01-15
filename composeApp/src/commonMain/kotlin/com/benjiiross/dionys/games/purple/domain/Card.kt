package com.benjiiross.dionys.games.purple.domain

data class Card(val rank: Rank, val suit: Suit) {
    val color: CardColor
        get() =
            when (suit) {
                Suit.HEARTS,
                Suit.DIAMONDS -> CardColor.RED
                Suit.CLUBS,
                Suit.SPADES -> CardColor.BLACK
            }

    val id: String = "${rank.symbol}${suit.symbol}"

    override fun toString(): String = id
}

enum class CardColor {
    RED,
    BLACK,
}

enum class PlayerChoice {
    RED,
    BLACK,
    PURPLE,
}

@Suppress("MagicNumber")
enum class Rank(val symbol: String, val value: Int) {
    ACE("A", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 11),
    QUEEN("Q", 12),
    KING("K", 13),
}

enum class Suit(val symbol: String) {
    HEARTS("♥"),
    DIAMONDS("♦"),
    CLUBS("♣"),
    SPADES("♠"),
}
