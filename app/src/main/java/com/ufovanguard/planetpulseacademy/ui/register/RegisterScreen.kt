package com.ufovanguard.planetpulseacademy.ui.register

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.data.Destination
import com.ufovanguard.planetpulseacademy.data.Destinations
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.uicomponent.PPATextField

@Composable
fun RegisterScreen(
	viewModel: RegisterViewModel,
	navigateTo: (Destination) -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(
		viewModel = viewModel,
		onEvent = { event ->
			when (event) {
				// when login is successful, navigate to home screen
				is RegisterUiEvent.RegisterSuccess -> {
					navigateTo(Destinations.home)
				}
			}
		}
	) { scaffoldPadding ->
		RegisterScreenContent(
			state = state,
			onPasswordVisibilityChanged = viewModel::showPassword,
			onEmailChanged = viewModel::setEmail,
			onUsernameChanged = viewModel::setUsername,
			onPasswordChanged = viewModel::setPassword,
			onRegisterClicked = viewModel::register,
			onLoginClicked = {
				navigateTo(Destinations.login)
			},
			modifier = Modifier
				.fillMaxSize()
				.padding(scaffoldPadding)
		)
	}

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun RegisterScreenContent(
	state: RegisterState,
	modifier: Modifier = Modifier,
	onPasswordVisibilityChanged: (Boolean) -> Unit = {},
	onEmailChanged: (String) -> Unit = {},
	onUsernameChanged: (String) -> Unit = {},
	onPasswordChanged: (String) -> Unit = {},
	onRegisterClicked: () -> Unit = {},
	onLoginClicked: () -> Unit = {},
) {

	val context = LocalContext.current
	val focusManager = LocalFocusManager.current
	val keyboardController = LocalSoftwareKeyboardController.current

	val rememberedOnEmailChanged by rememberUpdatedState(onEmailChanged)
	val rememberedOnUsernameChanged by rememberUpdatedState(onUsernameChanged)
	val rememberedOnPasswordChanged by rememberUpdatedState(onPasswordChanged)
	val rememberedOnRegisterClicked by rememberUpdatedState(onRegisterClicked)

	val (
		emailFocusRequester,
		usernameFocusRequester,
		passwordFocusRequester,
	) = FocusRequester.createRefs()

	var emailTextFieldValue by remember {
		mutableStateOf(
			TextFieldValue(
				text = state.email
			)
		)
	}

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
				singleLine = true,
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
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(usernameFocusRequester)
			)

			PPATextField(
				value = emailTextFieldValue,
				singleLine = true,
				keyboardOptions = KeyboardOptions(
					imeAction = ImeAction.Next,
					keyboardType = KeyboardType.Email
				),
				keyboardActions = KeyboardActions(
					onNext = {
						focusManager.moveFocus(FocusDirection.Next)
					}
				),
				onValueChange = { newValue ->
					emailTextFieldValue = newValue
					rememberedOnEmailChanged(emailTextFieldValue.text)
				},
				placeholder = {
					Text(stringResource(id = R.string.email))
				},
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(emailFocusRequester)
			)

			PPATextField(
				value = passwordTextFieldValue,
				singleLine = true,
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
				onClick = rememberedOnRegisterClicked,
				modifier = Modifier
					.fillMaxWidth()
			) {
				Text(stringResource(id = R.string.register))
			}
		}

		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.spacedBy(4.dp),
			modifier = Modifier
				.layoutId("bottomContent")
		) {
			Text(
				text = stringResource(id = R.string.already_have_an_account) + "?",
				style = MaterialTheme.typography.bodyMedium.copy(
					color = MaterialTheme.colorScheme.onBackground
				)
			)

			ClickableText(
				text = buildAnnotatedString {
					append(stringResource(id = R.string.login))
				},
				style = MaterialTheme.typography.bodyMedium.copy(
					color = MaterialTheme.colorScheme.primary
				),
				onClick = {
					onLoginClicked()
				}
			)
		}
	}

}
