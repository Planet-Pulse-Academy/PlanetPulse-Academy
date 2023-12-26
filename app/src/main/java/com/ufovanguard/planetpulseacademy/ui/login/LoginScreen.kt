package com.ufovanguard.planetpulseacademy.ui.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
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
import com.ufovanguard.planetpulseacademy.foundation.common.ValidatorResult.Success.parse
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
					navigateTo(Destinations.Main.home)
				}
			}
		}
	) { scaffoldPadding ->
		LoginScreenContent(
			state = state,
			onPasswordVisibilityChanged = viewModel::showPassword,
			onUsernameChanged = viewModel::setUsername,
			onPasswordChanged = viewModel::setPassword,
			onLoginClicked = viewModel::login,
			onRegisterClicked = {
				navigateTo(Destinations.Auth.register)
			},
			modifier = Modifier
				.fillMaxSize()
				.padding(scaffoldPadding)
		)
	}

}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
	ExperimentalLayoutApi::class
)
@Composable
private fun LoginScreenContent(
	state: LoginState,
	modifier: Modifier = Modifier,
	onPasswordVisibilityChanged: (Boolean) -> Unit = {},
	onUsernameChanged: (String) -> Unit = {},
	onPasswordChanged: (String) -> Unit = {},
	onRegisterClicked: () -> Unit = {},
	onLoginClicked: () -> Unit = {},
) {

	val context = LocalContext.current
	val focusManager = LocalFocusManager.current
	val keyboardController = LocalSoftwareKeyboardController.current

	val rememberedOnUsernameChanged by rememberUpdatedState(onUsernameChanged)
	val rememberedOnPasswordChanged by rememberUpdatedState(onPasswordChanged)
	val rememberedOnLoginClicked by rememberUpdatedState(onLoginClicked)

	val (
		usernameFocusRequester,
		passwordFocusRequester,
	) = FocusRequester.createRefs()

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

	val isImeVisible = WindowInsets.isImeVisible

	LaunchedEffect(isImeVisible) {
		if (!isImeVisible) focusManager.clearFocus()
	}

	if (state.isLoading) {
		BasicAlertDialog(
			onDismissRequest = {}
		) {
			Box(
				contentAlignment = Alignment.Center
			) {
				CircularProgressIndicator(
					modifier = Modifier
						.size(32.dp)
				)
			}
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
				singleLine = true,
				isError = state.usernameErrMsg != null,
				keyboardOptions = KeyboardOptions(
					imeAction = ImeAction.Next,
					keyboardType = KeyboardType.Text
				),
				keyboardActions = KeyboardActions(
					onNext = {
						focusManager.moveFocus(FocusDirection.Next)
					}
				),
				onValueChange = { newValue ->
					usernameTextFieldValue = newValue
					rememberedOnUsernameChanged(usernameTextFieldValue.text)
				},
				placeholder = {
					Text(stringResource(id = R.string.username))
				},
				supportingText = if (state.usernameErrMsg != null) {
					{
						Text(
							text = state.usernameErrMsg.parse(context),
							style = LocalTextStyle.current.copy(
								color = MaterialTheme.colorScheme.onBackground
							)
						)
					}
				} else null,
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(usernameFocusRequester)
			)

			PPATextField(
				value = passwordTextFieldValue,
				singleLine = true,
				isError = state.passwordErrMsg != null,
				visualTransformation = if (state.showPassword) VisualTransformation.None
				else PasswordVisualTransformation(),
				keyboardOptions = KeyboardOptions(
					imeAction = ImeAction.Done,
					keyboardType = KeyboardType.Password
				),
				keyboardActions = KeyboardActions(
					onDone = {
						keyboardController?.hide()
					}
				),
				onValueChange = { newValue ->
					passwordTextFieldValue = newValue
					rememberedOnPasswordChanged(passwordTextFieldValue.text)
				},
				placeholder = {
					Text(stringResource(id = R.string.password))
				},
				supportingText = if (state.passwordErrMsg != null) {
					{
						Text(
							text = state.passwordErrMsg.parse(context),
							style = LocalTextStyle.current.copy(
								color = MaterialTheme.colorScheme.onBackground
							)
						)
					}
				} else null,
				trailingIcon = {
					IconButton(
						onClick = {
							onPasswordVisibilityChanged(!state.showPassword)
						}
					) {
						AnimatedContent(
							label = "show_password_button",
							targetState = state.showPassword
						) { show ->
							Icon(
								painter = painterResource(
									id = if (show) R.drawable.ic_eye
									else R.drawable.ic_eye_slash
								),
								contentDescription = null
							)
						}
					}
				},
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(passwordFocusRequester)
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
					onRegisterClicked()
				}
			)
		}
	}

}
