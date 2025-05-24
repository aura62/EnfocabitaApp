
package com.aura.enfocabita.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Color palette extracted from mockups
val md_theme_light_primary = Color(0xFF4C8C4A)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_secondary = Color(0xFFB3D8B3)
val md_theme_light_onSecondary = Color(0xFF1F1F1F)
val md_theme_light_background = Color(0xFFF2F2F2)
val md_theme_light_surface = Color(0xFFEFF3EB)
val md_theme_light_error = Color(0xFFB00020)
val md_theme_light_onBackground = Color(0xFF1F1F1F)
val md_theme_light_onSurface = Color(0xFF1F1F1F)

val md_theme_dark_primary = Color(0xFFA2D18E)
val md_theme_dark_onPrimary = Color(0xFF1F1F1F)
val md_theme_dark_secondary = Color(0xFF8ABE8A)
val md_theme_dark_onSecondary = Color(0xFF1F1F1F)
val md_theme_dark_background = Color(0xFF1F1F1F)
val md_theme_dark_surface = Color(0xFF2E2E2E)
val md_theme_dark_error = Color(0xFFCF6679)
val md_theme_dark_onBackground = Color(0xFFE0E0E0)
val md_theme_dark_onSurface = Color(0xFFE0E0E0)
val bar_label = Color.Unspecified

private val LightColors: ColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    error = md_theme_light_error
)

private val DarkColors: ColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    error = md_theme_dark_error
)

// Typography
val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)

// Shapes
val AppShapes = Shapes(
    small = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
    large = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
)

@Composable
fun EnfocabitaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

// End of Theme.kt
