package com.ufovanguard.planetpulseacademy.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.data.Destination
import com.ufovanguard.planetpulseacademy.data.Destinations
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme
import com.ufovanguard.planetpulseacademy.foundation.uicomponent.PPATextField

class ThemeModePreviewParameterProvider: PreviewParameterProvider<Boolean> {
	override val values: Sequence<Boolean>
		get() = sequenceOf(true, false)
}

@Preview(device = "spec:width=392.7dp,height=850.9dp,dpi=440")
@Composable
private fun LoginScreenPreview(
	@PreviewParameter(ThemeModePreviewParameterProvider::class)
	isDarkTheme: Boolean
) {
	PlanetPulseAcademyTheme(
		darkTheme = isDarkTheme
	) {
		LoginScreenContent(
			state = LoginState(),
			modifier = Modifier
				.fillMaxSize()
				.background(MaterialTheme.colorScheme.background)
		)
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
				.fillMaxSize()
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

	var usernameTextFieldValue by remember {
		mutableStateOf(
			TextFieldValue(
				text = state.username
			)
		)
	}

	var passwordTextFieldValue by remember {
		mutableStateOf(
			TextFieldValue(
				text = state.password
			)
		)
	}

	val constraintSet = ConstraintSet {
		val (
			middleContent,
			bottomContent,
		) = createRefsFor(
			"middleContent",
			"bottomContent",
		)

		constrain(middleContent) {
			centerTo(parent)
		}

		constrain(bottomContent) {
			top.linkTo(middleContent.bottom)
			bottom.linkTo(parent.bottom)

			centerHorizontallyTo(parent)
		}
	}

	ConstraintLayout(
		constraintSet = constraintSet,
		modifier = modifier
			.padding(16.dp)
	) {
		Column(
			verticalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier
				.layoutId("middleContent")
		) {
			PPATextField(
				value = usernameTextFieldValue,
				onValueChange = { newValue ->
					usernameTextFieldValue = newValue
					rememberedOnUsernameChanged(usernameTextFieldValue.text)
				},
				placeholder = {
					Text(stringResource(id = R.string.username))
				},
				modifier = Modifier
					.fillMaxWidth()
			)

			PPATextField(
				value = passwordTextFieldValue,
				onValueChange = { newValue ->
					passwordTextFieldValue = newValue
					rememberedOnPasswordChanged(passwordTextFieldValue.text)
				},
				placeholder = {
					Text(stringResource(id = R.string.password))
				},
				modifier = Modifier
					.fillMaxWidth()
			)

			Button(
				shape = MaterialTheme.shapes.medium,
				onClick = rememberedOnLoginClicked,
				modifier = Modifier
					.fillMaxWidth()
			) {
				Text(stringResource(id = R.string.login))
			}

			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Text(
					text = stringResource(id = R.string.forgot_password) + "?",
					style = MaterialTheme.typography.bodyMedium.copy(
						color = MaterialTheme.colorScheme.onBackground
					)
				)

				ClickableText(
					text = buildAnnotatedString {
						append(stringResource(id = R.string.reset))
					},
					style = MaterialTheme.typography.bodyMedium.copy(
						color = MaterialTheme.colorScheme.primary
					),
					onClick = {
						// TODO: Reset password
					}
				)
			}
		}

		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(4.dp),
			modifier = Modifier
				.layoutId("bottomContent")
		) {
			Text(
				text = stringResource(id = R.string.dont_have_an_account) + "?",
				style = MaterialTheme.typography.bodyMedium.copy(
					color = MaterialTheme.colorScheme.onBackground
				)
			)

			ClickableText(
				text = buildAnnotatedString {
					append(stringResource(id = R.string.create_account))
				},
				style = MaterialTheme.typography.bodyMedium.copy(
					color = MaterialTheme.colorScheme.primary
				),
				onClick = {
					// TODO: Register
				}
			)
		}
	}

}
