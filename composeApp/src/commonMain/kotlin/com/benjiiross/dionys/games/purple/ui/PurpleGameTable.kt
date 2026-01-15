package com.benjiiross.dionys.games.purple.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.benjiiross.dionys.common.ui.theme.PurpleTheme
import com.benjiiross.dionys.games.purple.domain.Card
import com.benjiiross.dionys.games.purple.domain.PlayerChoice
import com.benjiiross.dionys.games.purple.domain.Rank
import com.benjiiross.dionys.games.purple.domain.Suit
import com.benjiiross.dionys.games.purple.viewmodel.PurpleUiState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PurpleGameTable(
    state: PurpleUiState,
    onChoiceSelected: (PlayerChoice) -> Unit,
    onDrawCard: () -> Unit,
    onPassTurn: () -> Unit,
    onCardLanded: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        PurpleGameTableTop(state = state)

        PurpleGameTableMiddle(
            state = state,
            onDrawCard = onDrawCard,
            onAnimationFinished = onCardLanded,
        )

        PurpleGameTableBottom(
            state = state,
            onPassTurn = onPassTurn,
            onChoiceSelected = onChoiceSelected,
        )
    }
}

@Composable
@Preview
private fun PurpleGameTablePreview() {
    val mockState =
        PurpleUiState(
            currentPlayer = "yayou",
            streak = 3,
            remainingCards = 42,
            turnSuccesses = 0,
            playerChoice = PlayerChoice.PURPLE,
            drawnCards =
                listOf(
                    Card(Rank.ACE, Suit.HEARTS),
                    Card(Rank.TWO, Suit.HEARTS),
                    Card(Rank.THREE, Suit.HEARTS),
                ),
            isTurnLost = false,
        )

    PurpleTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            PurpleGameTable(
                state = mockState,
                onChoiceSelected = {},
                onDrawCard = {},
                onPassTurn = {},
                onCardLanded = {},
            )
        }
    }
}

@Composable
@Preview
private fun PurpleGameTablePreview2() {
    val mockState =
        PurpleUiState(
            currentPlayer = "yayou",
            streak = 0,
            turnSuccesses = 3,
            remainingCards = 42,
            playerChoice = null,
            drawnCards =
                listOf(
                    Card(Rank.ACE, Suit.HEARTS),
                    Card(Rank.TWO, Suit.HEARTS),
                    Card(Rank.THREE, Suit.HEARTS),
                ),
            isTurnLost = false,
        )

    PurpleTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            PurpleGameTable(
                state = mockState,
                onChoiceSelected = {},
                onDrawCard = {},
                onPassTurn = {},
                onCardLanded = {},
            )
        }
    }
}
