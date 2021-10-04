package sanmi.labs.spotifyclone.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Green,
    primaryVariant = Green500,
    surface = Black,
    onSurface = White
)

private val LightColorPalette = lightColors(
    primary = Green,
    primaryVariant = Green500,
    surface = White,
    onSurface = Black

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

private val LightButtonDefaults = darkColors(
    background = Green,
    onBackground = Black
)

private val DarkButtonDefaults = lightColors(
    background = Green,
    onBackground = White
)

@Composable
fun SpotifyCloneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}