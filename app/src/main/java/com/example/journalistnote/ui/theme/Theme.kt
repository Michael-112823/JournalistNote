package com.example.journalistnote.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Brown80,
    secondary = BrownGrey80,
    tertiary = BrownDark80,
    primaryContainer = Color(0xFF4E342E),
    onPrimaryContainer = Color(0xFFD7CCC8),
    surface = Color(0xFF1C1B1F),
    background = Color(0xFF1C1B1F)
)

private val LightColorScheme = lightColorScheme(
    primary = Brown40,
    secondary = BrownGrey40,
    tertiary = BrownDark40,
    primaryContainer = Color(0xFFEFEBE9),
    onPrimaryContainer = Color(0xFF3E2723),
    surface = Color(0xFFFFFBFE),
    background = Color(0xFFFFFBFE)
)

@Composable
fun JournalistNoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
