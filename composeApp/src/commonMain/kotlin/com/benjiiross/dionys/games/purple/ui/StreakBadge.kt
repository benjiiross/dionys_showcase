package com.benjiiross.dionys.games.purple.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.benjiiross.dionys.common.ui.composables.DionysHaptic
import com.benjiiross.dionys.common.ui.composables.DionysInteractiveSurface
import com.benjiiross.dionys.common.ui.composables.DionysSurfaceSize
import com.benjiiross.dionys.common.ui.composables.DionysTextBody

private const val GrowthScale = 0.05f
private const val StreakBaseRotation = 10f
private const val MinAnimationThreshold = 3
const val MaxAnimationThreshold = 5

private const val StreakShakeDurationMs = 50

@Composable
fun StreakBadge(count: Int, modifier: Modifier = Modifier) {
    if (count <= 0) return

    val (growthScale, bounceScale, shakeOffset) = rememberStreakAnimations(count)

    val (startColor, endColor) = rememberStreakColors(count)

    DionysHaptic(count)

    DionysInteractiveSurface(
        onClick = {},
        showPressed = false,
        showPulse = count >= MinAnimationThreshold,
        surfaceSize = DionysSurfaceSize.Small,
        backgroundColor = Color.Transparent,
        modifier =
            modifier.size(width = 48.dp, height = 72.dp).graphicsLayer {
                val finalScale = growthScale * bounceScale
                scaleX = finalScale
                scaleY = finalScale
                rotationZ = StreakBaseRotation + shakeOffset
            },
    ) {
        StreakBadgeContent(count = count, startColor = startColor, endColor = endColor)
    }
}

@Composable
private fun rememberStreakAnimations(count: Int): Triple<Float, Float, Float> {
    val growthScale by
        animateFloatAsState(
            targetValue = (1f + (count * GrowthScale)),
            animationSpec = spring(stiffness = Spring.StiffnessLow),
            label = "GrowthScale",
        )

    val bounceScale by
        animateFloatAsState(
            targetValue = if (count > 0) 1f else 0.8f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
            label = "StreakBounce",
        )

    val shakeOffset =
        if (count >= MaxAnimationThreshold) {
            val infiniteTransition = rememberInfiniteTransition(label = "shake")
            infiniteTransition
                .animateFloat(
                    initialValue = -2f,
                    targetValue = 2f,
                    animationSpec =
                        infiniteRepeatable(
                            animation = tween(StreakShakeDurationMs, easing = LinearEasing),
                            repeatMode = RepeatMode.Reverse,
                        ),
                    label = "shakeAngle",
                )
                .value
        } else 0f

    return Triple(growthScale, bounceScale, shakeOffset)
}

@Composable
private fun rememberStreakColors(count: Int): Pair<Color, Color> {
    val startColor =
        when {
            count >= MaxAnimationThreshold -> MaterialTheme.colorScheme.primaryContainer
            count >= MinAnimationThreshold -> MaterialTheme.colorScheme.secondaryContainer
            else -> MaterialTheme.colorScheme.surfaceVariant
        }
    val endColor =
        when {
            count >= MaxAnimationThreshold -> MaterialTheme.colorScheme.primary
            count >= MinAnimationThreshold -> MaterialTheme.colorScheme.primaryContainer
            else -> MaterialTheme.colorScheme.surfaceVariant
        }

    val animatedStart by animateColorAsState(startColor)
    val animatedEnd by animateColorAsState(endColor)

    return Pair(animatedStart, animatedEnd)
}

@Composable
private fun StreakBadgeContent(count: Int, startColor: Color, endColor: Color) {
    Box(
        modifier =
            Modifier.fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(colors = listOf(startColor, endColor)),
                    shape = RoundedCornerShape(12.dp),
                ),
        contentAlignment = Alignment.Center,
    ) {
        DionysTextBody(
            text = "x$count",
            color =
                if (count >= MaxAnimationThreshold) Color.White
                else MaterialTheme.colorScheme.onSurface,
        )
    }
}
