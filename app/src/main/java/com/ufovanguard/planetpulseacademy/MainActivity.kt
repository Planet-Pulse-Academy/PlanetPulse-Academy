package com.ufovanguard.planetpulseacademy

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme
import com.ufovanguard.planetpulseacademy.ui.app.PlanetPulseAcademy
import com.ufovanguard.planetpulseacademy.ui.app.PlanetPulseAcademyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

	private val viewModel: PlanetPulseAcademyViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		// Set system bar and navigation bar color to transparent
		enableEdgeToEdge(
			navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
			statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
		)

		super.onCreate(savedInstanceState)

		installSplashScreen().setKeepOnScreenCondition {
			viewModel.state.value.userCredential == null ||
			viewModel.state.value.userPreference == null
		}

		WindowCompat.setDecorFitsSystemWindows(window, false)

		setContent {
			PlanetPulseAcademyTheme {
				CompositionLocalProvider(
					LocalContentColor provides MaterialTheme.colorScheme.onBackground
				) {
					Surface(
						color = MaterialTheme.colorScheme.background,
						contentColor = MaterialTheme.colorScheme.onBackground,
						modifier = Modifier
							.fillMaxSize()
					) {
						PlanetPulseAcademy(
							viewModel = viewModel
						)
					}
				}
			}
		}
	}

}
