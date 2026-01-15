package com.benjiiross.dionys.games.purple.viewmodel

import androidx.lifecycle.ViewModel
import com.benjiiross.dionys.common.ui.players.PlayerBaseState
import com.benjiiross.dionys.common.ui.players.PlayerViewModelHelper
import com.benjiiross.dionys.games.purple.domain.Card
import com.benjiiross.dionys.games.purple.domain.CardColor
import com.benjiiross.dionys.games.purple.domain.PlayerChoice
import com.benjiiross.dionys.games.purple.domain.PurpleCardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class PurpleGameStep {
    PlayerList,
    Info,
    Game,
    GameOver,
}

data class PurpleUiState(
    override val players: List<String> = emptyList(),
    val step: PurpleGameStep = PurpleGameStep.Info,
    val currentPlayer: String? = null,
    val remainingCards: Int = 52,
    val drawnCards: List<Card> = emptyList(),
    val streak: Int = 0,
    val turnSuccesses: Int = 0,
    val playerChoice: PlayerChoice? = null,
    val isTurnLost: Boolean = false,
    val cardInTransit: Card? = null,
    val transitQueue: List<Card> = emptyList(),
) : PlayerBaseState {
    val canPass: Boolean
        get() = turnSuccesses >= 2 && !isTurnLost

    fun isChoicePossible(choice: PlayerChoice): Boolean {
        val countNeeded = if (choice == PlayerChoice.PURPLE) 2 else 1
        return remainingCards >= countNeeded
    }
}

class PurpleViewModel(
    private val repository: PurpleCardRepository,
    private val playerHelper: PlayerViewModelHelper = PlayerViewModelHelper(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(PurpleUiState())
    val uiState: StateFlow<PurpleUiState> = _uiState.asStateFlow()

    fun onShowGameInfo() {
        _uiState.update { it.copy(step = PurpleGameStep.PlayerList) }
    }

    fun onAddPlayer(name: String) {
        _uiState.update {
            it.copy(players = playerHelper.addPlayer(currentPlayers = it.players, name))
        }
    }

    fun removePlayer(name: String) {
        _uiState.update {
            it.copy(players = playerHelper.removePlayer(currentPlayers = it.players, name))
        }
    }

    fun onValidatePlayers() {
        if (playerHelper.hasEnoughPlayers(_uiState.value.players)) {
            _uiState.update { it.copy(step = PurpleGameStep.Game) }
            startNewTurn()
        }
    }

    private fun startNewTurn() {
        val current = _uiState.value
        val currentIndex =
            if (current.currentPlayer == null) -1
            else current.players.indexOf(current.currentPlayer)
        val nextIndex = (currentIndex + 1) % current.players.size

        _uiState.update {
            it.copy(
                currentPlayer = it.players[nextIndex],
                drawnCards = emptyList(),
                streak = 0,
                playerChoice = null,
                isTurnLost = false,
            )
        }
    }

    fun onChoiceSelected(choice: PlayerChoice) {
        if (!_uiState.value.isTurnLost) {
            _uiState.update { it.copy(playerChoice = choice) }
        }
    }

    fun onDrawCard() {
        val current = _uiState.value

        if (current.cardInTransit != null) return

        val choice = current.playerChoice ?: return
        val drawCount = if (choice == PlayerChoice.PURPLE) 2 else 1

        val newCards = List(size = drawCount) { repository.drawCard() }
        val remaining = repository.getRemainingCardsCount()

        _uiState.update {
            it.copy(
                remainingCards = remaining,
                cardInTransit = newCards.first(),
                transitQueue = newCards.drop(1),
            )
        }
    }

    fun onCardLanded() {
        val current = _uiState.value
        val landedCard = current.cardInTransit ?: return
        val choice = current.playerChoice ?: return

        val newDrawnCards = current.drawnCards + landedCard
        val nextCard = current.transitQueue.firstOrNull()
        val remainingQueue = current.transitQueue.drop(1)

        val isSequenceFinished = nextCard == null

        if (!isSequenceFinished) {
            _uiState.update {
                it.copy(
                    drawnCards = newDrawnCards,
                    cardInTransit = nextCard,
                    transitQueue = remainingQueue,
                )
            }
        } else {
            val cardsToVerify =
                if (choice == PlayerChoice.PURPLE) {
                    newDrawnCards.takeLast(2)
                } else {
                    newDrawnCards.takeLast(1)
                }

            val isSuccess =
                when (choice) {
                    PlayerChoice.RED -> cardsToVerify.first().color == CardColor.RED
                    PlayerChoice.BLACK -> cardsToVerify.first().color == CardColor.BLACK
                    PlayerChoice.PURPLE -> {
                        val hasRed = cardsToVerify.any { it.color == CardColor.RED }
                        val hasBlack = cardsToVerify.any { it.color == CardColor.BLACK }
                        hasRed && hasBlack
                    }
                }

            val drawCount = if (choice == PlayerChoice.PURPLE) 2 else 1

            _uiState.update {
                it.copy(
                    drawnCards = newDrawnCards,
                    cardInTransit = null,
                    transitQueue = emptyList(),
                    playerChoice = null,
                    streak = if (isSuccess) it.streak + drawCount else it.streak,
                    turnSuccesses =
                        if (isSuccess) it.turnSuccesses + drawCount else it.turnSuccesses,
                    isTurnLost = !isSuccess,
                )
            }

            if (current.remainingCards == 0) {
                _uiState.update { it.copy(step = PurpleGameStep.GameOver) }
            }
        }
    }

    fun onPassTurn() {
        val isLastTurnWon = !_uiState.value.isTurnLost

        val current = _uiState.value
        val currentIndex =
            if (current.currentPlayer == null) -1
            else current.players.indexOf(current.currentPlayer)
        val nextIndex = (currentIndex + 1) % current.players.size

        _uiState.update {
            it.copy(
                currentPlayer = it.players[nextIndex],
                playerChoice = null,
                isTurnLost = false,
                drawnCards = if (isLastTurnWon) it.drawnCards else emptyList(),
                streak = if (isLastTurnWon) it.streak else 0,
                turnSuccesses = 0,
            )
        }
    }

    fun onResetDeck() {
        repository.resetDeck()
        _uiState.update {
            it.copy(
                step = PurpleGameStep.Game,
                remainingCards = repository.getRemainingCardsCount(),
            )
        }
        startNewTurn()
    }
}
