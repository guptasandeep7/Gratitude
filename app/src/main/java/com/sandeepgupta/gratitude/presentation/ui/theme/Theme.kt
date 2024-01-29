package com.sandeepgupta.gratitude.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LightColors = lightColorScheme(
    primary = light_primary,
    onPrimary = light_on_primary,
    background = light_background,
    surface = light_surface,
    onSurface = light_on_surface,
    onSurfaceVariant = light_on_surface_variant,
    outline = light_outline,
    outlineVariant = light_outline_variant,
    secondaryContainer = light_secondary_container,
    onSecondaryContainer = light_on_secondary_container,
    surfaceVariant = light_surface_variant
)


private val DarkColors = darkColorScheme(
    primary = dark_primary,
    onPrimary = dark_on_primary,
    background = dark_background,
    surface = dark_surface,
    onSurface = dark_on_surface,
    onSurfaceVariant = dark_on_surface,
    outline = dark_outline,
    outlineVariant = dark_outline_variant,
    secondaryContainer = dark_secondary_container,
    onSecondaryContainer = dark_on_secondary_container,
    surfaceVariant = dark_surface_variant
)

@Composable
fun GratitudeTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = if(!useDarkTheme) light_background.toArgb() else dark_background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !useDarkTheme
        }
    }

    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content,
        typography = Typography
    )
}