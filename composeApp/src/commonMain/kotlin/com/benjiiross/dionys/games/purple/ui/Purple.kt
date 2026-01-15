package com.benjiiross.dionys.games.purple.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.benjiiross.dionys.common.ui.composables.GameBar
import com.benjiiross.dionys.common.ui.composables.gamerules.GameRulesPopup
import com.benjiiross.dionys.common.ui.composables.gamerules.GameRulesScreen
import com.benjiiross.dionys.common.ui.navigation.AppScreens
import com.benjiiross.dionys.common.ui.players.PlayerList
import com.benjiiross.dionys.common.ui.theme.PurpleTheme
import com.benjiiross.dionys.games.purple.viewmodel.PurpleGameStep
import com.benjiiross.dionys.games.purple.viewmodel.PurpleViewModel
import dionys.composeapp.generated.resources.Res
import dionys.composeapp.generated.resources.purple_rules
import org.koin.compose.koinInject

@Composable
fun Purple(viewModel: PurpleViewModel = koinInject(), navigateToHome: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showRulesDialog by rememberSaveable { mutableStateOf(false) }

    PurpleTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                GameBar(
                    currentScreen = AppScreens.Purple,
                    navigateToHome = navigateToHome,
                    onInfoClick = { showRulesDialog = true },
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
            contentWindowInsets = WindowInsets.safeDrawing,
        ) { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                when (uiState.step) {
                    PurpleGameStep.Info ->
                        GameRulesScreen(
                            onShowGameInfo = viewModel::onShowGameInfo,
                            gameRules = Res.string.purple_rules,
                        )

                    PurpleGameStep.PlayerList ->
                        PlayerList(
                            players = uiState.players,
                            onAddPlayer = viewModel::onAddPlayer,
                            onRemovePlayer = viewModel::removePlayer,
                            onValidate = viewModel::onValidatePlayers,
                        )

                    PurpleGameStep.Game -> {
                        if (uiState.currentPlayer == null) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        } else {
                            PurpleGameTable(
                                state = uiState,
                                onChoiceSelected = viewModel::onChoiceSelected,
                                onDrawCard = viewModel::onDrawCard,
                                onPassTurn = viewModel::onPassTurn,
                                onCardLanded = viewModel::onCardLanded,
                            )
                        }
                    }

                    PurpleGameStep.GameOver -> {
                        PurpleGameOver(onResetDeck = viewModel::onResetDeck)
                    }
                }

                if (showRulesDialog) {
                    GameRulesPopup(
                        gameRules = Res.string.purple_rules,
                        onDismiss = { showRulesDialog = false },
                    )
                }
            }
        }
    }
}
