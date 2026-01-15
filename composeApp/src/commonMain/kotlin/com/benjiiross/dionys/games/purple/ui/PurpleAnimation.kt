package com.benjiiross.dionys.games.purple.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.benjiiross.dionys.common.ui.composables.cards.CardAspectRatio
import com.benjiiross.dionys.common.ui.composables.cards.CardCornerRadius
import com.benjiiross.dionys.common.ui.composables.cards.CardHorizontalRotation
import com.benjiiross.dionys.common.ui.composables.cards.GameCardBack
import com.benjiiross.dionys.common.ui.composables.cards.GameCardFace
import com.benjiiross.dionys.games.purple.domain.Card
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun FlyingCardOverlay(
    card: Card?,
    startPosition: Offset,
    endPosition: Offset,
    onAnimationFinished: () -> Unit,
) {
    if (card == null) return

    if (startPosition == Offset.Zero || endPosition == Offset.Zero) {
        LaunchedEffect(card) { onAnimationFinished() }
        return
    }

    val animatable = remember(card) { Animatable(0f) }

    LaunchedEffect(card) {
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        )
        onAnimationFinished()
    }

    val progress = animatable.value

    val currentX = startPosition.x + (endPosition.x - startPosition.x) * progress
    val currentY = startPosition.y + (endPosition.y - startPosition.y) * progress

    val rotationY = progress * 180f
    val scale = 1f + (0.4f * sin(progress * PI).toFloat())

    Box(
        modifier =
            Modifier.offset { IntOffset(currentX.roundToInt(), currentY.roundToInt()) }
                .size(width = (200.dp * CardAspectRatio), height = 200.dp)
                .graphicsLayer {
                    this.rotationY = rotationY
                    this.scaleX = scale
                    this.scaleY = scale
                    cameraDistance = CardCornerRadius * density
                }
    ) {
        if (rotationY <= CardHorizontalRotation) {
            GameCardBack(modifier = Modifier.fillMaxSize())
        } else {
            GameCardFace(
                card = card,
                modifier = Modifier.fillMaxSize().graphicsLayer { scaleX = -1f },
            )
        }
    }
}
