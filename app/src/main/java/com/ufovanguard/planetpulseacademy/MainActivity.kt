package com.ufovanguard.planetpulseacademy

import android.content.res.Configuration
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
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme
import com.ufovanguard.planetpulseacademy.ui.app.PlanetPulseAcademy
import com.ufovanguard.planetpulseacademy.ui.app.PlanetPulseAcademyViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

	private val viewModel: PlanetPulseAcademyViewModel by viewModels()

	private val credentialManager by lazy {
		CredentialManager.create(application)
	}

	override fun onCreate(savedInstanceState: Bundle?) {

		val isDarkTheme = resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

		// Set system bar and navigation bar color to transparent
		enableEdgeToEdge(
			statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
			navigationBarStyle = if (isDarkTheme) SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
			else SystemBarStyle.dark(Color.TRANSPARENT)
		)

		installSplashScreen().setKeepOnScreenCondition {
			viewModel.state.value.userCredential == null ||
				viewModel.state.value.userPreference == null
		}

		super.onCreate(savedInstanceState)

		WindowCompat.setDecorFitsSystemWindows(window, false)

		// Hide action bar in android 12 above
		actionBar?.hide()

		setContent {
			PlanetPulseAcademyTheme {
				CompositionLocalProvider(
					LocalContentColor provides MaterialTheme.colorScheme.onBackground
				) {
					Surface(
						color = PPATheme.colorScheme.background,
						contentColor = PPATheme.colorScheme.onBackground,
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

	suspend fun saveCredential(
		username: String,
		password: String,
		onSaved: () -> Unit = {},
		onError: () -> Unit = {}
	) {
		try {
			//Ask the user for permission to add the credentials
			credentialManager.createCredential(
				request = CreatePasswordRequest(username, password),
				context = this
			)

			onSaved()

			Timber.i("Credentials successfully added")
		} catch (e: CreateCredentialCancellationException) {
			//do nothing, the user chose not to save the credential
			Timber.e("User cancelled the save")
			onError()
		} catch (e: CreateCredentialException) {
			Timber.e("Credential save error", e)
			onError()
		}
	}

	suspend fun getCredential(): PasswordCredential? {
		try {
			val getRequest = GetCredentialRequest(
				credentialOptions = listOf(GetPasswordOption())
			)

			val credentialResponse = credentialManager.getCredential(
				request = getRequest,
				context = this
			)

			return credentialResponse.credential as? PasswordCredential
		} catch (e: GetCredentialCancellationException) {
			Timber.i("User cancelled the request")
			Timber.i(e)
		} catch (e: NoCredentialException) {
			Timber.i("No credential saved")
			Timber.i(e)
		} catch (e: GetCredentialException) {
			Timber.e(e)
		}

		return null
	}

}
