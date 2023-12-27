package com.ufovanguard.planetpulseacademy.foundation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class PPAColorScheme(
	val primary: Color,
	val primaryContainer: Color,
	val onPrimary: Color,
	val onPrimaryContainer: Color,
	val background: Color,
	val onBackground: Color,
	val inverseOnBackground: Color,
	val button: Color,
)

val LocalPPAContentColor = compositionLocalOf { Color.Unspecified }

val LocalPPAColorScheme = staticCompositionLocalOf {
	PPAColorScheme(
		primary = Color.Unspecified,
		primaryContainer = Color.Unspecified,
		onPrimary = Color.Unspecified,
		onPrimaryContainer = Color.Unspecified,
		background = Color.Unspecified,
		onBackground = Color.Unspecified,
		inverseOnBackground = Color.Unspecified,
		button = Color.Unspecified,
	)
}

val LocalPPATypography = staticCompositionLocalOf {
	Typography()
}

object PPATheme {
	val colorScheme: PPAColorScheme
		@Composable
		get() = LocalPPAColorScheme.current

	val typography: Typography
		@Composable
		get() = LocalPPATypography.current
}
