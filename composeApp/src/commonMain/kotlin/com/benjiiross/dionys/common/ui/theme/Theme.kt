package com.benjiiross.dionys.common.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dionys.composeapp.generated.resources.Res
import dionys.composeapp.generated.resources.montserrat_bold
import dionys.composeapp.generated.resources.montserrat_extra_bold
import dionys.composeapp.generated.resources.montserrat_medium
import dionys.composeapp.generated.resources.montserrat_regular
import org.jetbrains.compose.resources.Font

private val HomeColors =
    lightColorScheme(background = homeBackground, surface = surfaceColor, onSurface = Color.Black)

private val TodColors =
    lightColorScheme(
        background = todBackground,
        surface = surfaceColor,
        onSurface = Color.Black,
        primary = todTruth,
        onPrimary = Color.White,
        secondary = todDare,
        onSecondary = Color.White,
    )

private val PurpleColors =
    lightColorScheme(
        background = purpleBackground,
        onBackground = Color.White,
        surface = surfaceColor,
        onSurface = Color.Black,
        primary = purplePrimary,
        onPrimary = Color.White,
        secondary = purpleSecondary,
        onSecondary = Color.White,
        tertiary = purpleTertiary,
        onTertiary = Color.White,
        primaryContainer = purpleStreakSecondary,
        secondaryContainer = purpleStreakTertiary,
    )

private val TimeoutColors =
    lightColorScheme(
        background = timeoutBackground,
        onBackground = Color.White,
        surface = surfaceColor,
        onSurface = Color.Black,
        primary = timeoutPrimary,
        onPrimary = timeoutOnPrimary,
        secondary = timeoutDiceColor,
        onSecondary = timeoutDotColor,
    )

@Composable
private fun montserratTypography(): Typography {
    val montserratFont =
        FontFamily(
            Font(Res.font.montserrat_regular, FontWeight.Normal),
            Font(Res.font.montserrat_medium, FontWeight.Medium),
            Font(Res.font.montserrat_bold, FontWeight.Bold),
            Font(Res.font.montserrat_extra_bold, FontWeight.ExtraBold),
        )

    return Typography(
        displayLarge =
            TextStyle(fontFamily = montserratFont, fontWeight = FontWeight.Bold, fontSize = 32.sp),
        headlineLarge =
            TextStyle(
                fontFamily = montserratFont,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
            ),
        titleLarge =
            TextStyle(
                fontFamily = montserratFont,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = montserratFont,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                lineHeight = 24.sp,
            ),
        bodySmall =
            TextStyle(
                fontFamily = montserratFont,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 18.sp,
            ),
    )
}

object Gaps {
    val S = 12.dp
    val L = 24.dp
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = HomeColors, typography = montserratTypography(), content = content)
}

@Composable
fun TodTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = TodColors, typography = montserratTypography(), content = content)
}

@Composable
fun PurpleTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PurpleColors,
        typography = montserratTypography(),
        content = content,
    )
}

@Composable
fun TimeoutTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TimeoutColors,
        typography = montserratTypography(),
        content = content,
    )
}
