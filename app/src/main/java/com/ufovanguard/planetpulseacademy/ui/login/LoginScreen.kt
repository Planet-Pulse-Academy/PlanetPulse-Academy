package com.ufovanguard.planetpulseacademy.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufovanguard.planetpulseacademy.data.Destination
import com.ufovanguard.planetpulseacademy.data.Destinations
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.common.ValidatorResult.Success.parse
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
	PlanetPulseAcademyTheme {
		LoginScreenContent(state = LoginState())
	}
}

@Composable
fun LoginScreen(
	viewModel: LoginViewModel,
	navigateTo: (Destination) -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(
		viewModel = viewModel,
		onEvent = { event ->
			when (event) {
				// when login is successful, navigate to home screen
				is LoginUiEvent.LoginSuccess -> {
					navigateTo(Destinations.home)
				}
			}
		}
	) { scaffoldPadding ->
		LoginScreenContent(
			state = state,
			onUsernameChanged = viewModel::setUsername,
			onPasswordChanged = viewModel::setPassword,
			onLoginClicked = viewModel::login,
			modifier = Modifier
				.padding(scaffoldPadding)
		)
	}

}

@Composable
private fun LoginScreenContent(
	state: LoginState,
	modifier: Modifier = Modifier,
	onUsernameChanged: (String) -> Unit = {},
	onPasswordChanged: (String) -> Unit = {},
	onLoginClicked: () -> Unit = {}
) {

	val context = LocalContext.current

	val rememberedOnUsernameChanged by rememberUpdatedState(onUsernameChanged)
	val rememberedOnPasswordChanged by rememberUpdatedState(onPasswordChanged)
	val rememberedOnLoginClicked by rememberUpdatedState(onLoginClicked)

	Column(
		modifier = modifier
	) {
		TextField(
			value = state.username,
			onValueChange = rememberedOnUsernameChanged,
		)

		TextField(
			value = state.password,
			onValueChange = rememberedOnPasswordChanged,
			supportingText = if (state.passwordErrMsg != null) {
				{
					Text(state.passwordErrMsg.parse(context))
				}
			} else null
		)

		Button(
			onClick = rememberedOnLoginClicked
		) {
			Text("Login")
		}
	}
}
