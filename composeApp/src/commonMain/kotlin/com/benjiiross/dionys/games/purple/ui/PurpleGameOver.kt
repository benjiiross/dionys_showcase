package com.benjiiross.dionys.games.purple.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.benjiiross.dionys.common.ui.composables.DionysTextBody
import com.benjiiross.dionys.common.ui.composables.DionysTextTitleRounded
import com.benjiiross.dionys.common.ui.composables.PrimaryLargeButton
import com.benjiiross.dionys.common.ui.theme.Gaps
import com.benjiiross.dionys.common.ui.theme.PurpleTheme
import dionys.composeapp.generated.resources.Res
import dionys.composeapp.generated.resources.purple_game_over_message
import dionys.composeapp.generated.resources.purple_game_over_title
import dionys.composeapp.generated.resources.purple_restart_game_button
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PurpleGameOver(onResetDeck: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxSize().padding(Gaps.L),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Gaps.L),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DionysTextTitleRounded(textRes = Res.string.purple_game_over_title)

            DionysTextBody(
                textRes = Res.string.purple_game_over_message,
                color = MaterialTheme.colorScheme.onBackground,
            )

            PrimaryLargeButton(
                textRes = Res.string.purple_restart_game_button,
                onClick = onResetDeck,
                backgroundColor = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}

@Composable
@Preview
private fun GameOverPreview() {
    PurpleTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            PurpleGameOver(onResetDeck = {})
        }
    }
}
