package ar.edu.itba.hci.wallx.ui.theme

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

/*private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    
)*/
/*
private val DarkColorScheme = darkColorScheme(
    primary = Selected,
    onPrimary = Black,
    primaryContainer = Primary,
    onPrimaryContainer = White,

    secondary = Secondary,
    onSecondary = Black,
    secondaryContainer = SecondaryDarken1,
    onSecondaryContainer = White,

    background = Black,
    onBackground = White,

    surface = Surface,
    onSurface = White,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = White,

    error = Error,
    onError = White,
    errorContainer = Warning,
    onErrorContainer = Black,

    inversePrimary = Secondary,
    inverseSurface = Surface,
    inverseOnSurface = Black,

    outline = Accent,
    outlineVariant = SurfaceLight,

    scrim = SurfaceVariant,
    surfaceTint = Selected,

    tertiary = ButtonColor,
    onTertiary = Black,
    tertiaryContainer = Accent,
    onTertiaryContainer = White
)


private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = White,
    primaryContainer = Selected,
    onPrimaryContainer = Black,

    secondary = Secondary,
    onSecondary = Black,
    secondaryContainer = SecondaryDarken1,
    onSecondaryContainer = White,

    background = Background,
    onBackground = Black,

    surface = Surface,
    onSurface = Black,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = Black,

    error = Error,
    onError = White,
    errorContainer = Warning,
    onErrorContainer = Black,

    inversePrimary = PrimaryDarken1,
    inverseSurface = SurfaceVariant,
    inverseOnSurface = SurfaceLight,

    outline = Interactive,
    outlineVariant = SurfaceLight,

    scrim = SurfaceLight,
    surfaceTint = Primary,

    tertiary = ButtonColor,
    onTertiary = Black,
    tertiaryContainer = Accent,
    onTertiaryContainer = Black
)
*/


val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = White,
    primaryContainer = Secondary,
    onPrimaryContainer = Black,


    secondary = Secondary,
    onSecondary = Black,
    secondaryContainer = SecondaryDarken1,
    onSecondaryContainer = Black,

    tertiary = Tertiary,
    onTertiary = Black,
    tertiaryContainer = SurfaceLight,
    onTertiaryContainer = Black,

    background = Background,
    onBackground = Black,
    surface = Surface,
    onSurface = Black,

    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,

    error = Error,
    onError = White,
    errorContainer = Color(0xFFFCD8DF),
    onErrorContainer = Black,

    outline = Outline,
    outlineVariant = OutlineVariant,

    scrim = Scrim,
    inverseSurface = Color(0xFF2A3F38),
    inverseOnSurface = White,
    inversePrimary = PrimaryDarken1,
)


val DarkColorScheme = darkColorScheme(
    primary = PrimaryDarken1,
    onPrimary = White,
    primaryContainer = SecondaryDarken1,
    onPrimaryContainer = White,

    secondary = SecondaryDarken1,
    onSecondary = White,
    secondaryContainer = PrimaryDarken1,
    onSecondaryContainer = White,

    tertiary = TertiaryDark,
    onTertiary = White,
    tertiaryContainer = SurfaceDim,
    onTertiaryContainer = White,

    background = Black,
    onBackground = White,
    surface = SurfaceBrightDark,
    onSurface = White,

    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,

    error = Error,
    onError = White,
    errorContainer = Color(0xFF601410),
    onErrorContainer = White,

    outline = OutlineVariant,
    outlineVariant = Outline,

    scrim = Scrim,
    inverseSurface = Background,
    inverseOnSurface = Black,
    inversePrimary = Primary,
)



@Composable
fun WallxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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