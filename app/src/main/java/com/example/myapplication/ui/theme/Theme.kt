package com.example.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.Poppins
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.platform.LocalContext

val AppTypography = Typography(
    titleMedium = TextStyle(
        fontFamily = Poppins ,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Poppins ,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Poppins ,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun HelloJetpackComposeTheme(
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
}

private val LightThemeColors = lightColors(
    primary = Color(0xFF1E88E5),
    primaryVariant = Color(0xFF1565C0),
    secondary = Color(0xFF03DAC5)
)

@Composable
fun PanduanWisataTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightThemeColors,
        content = content,
    )
}
