package com.ufovanguard.planetpulseacademy.ui.register

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseScreenWrapper
import com.ufovanguard.planetpulseacademy.foundation.common.validator.ValidatorResult.Success.parse
import com.ufovanguard.planetpulseacademy.foundation.extension.preventSelection
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme
import com.ufovanguard.planetpulseacademy.foundation.uicomponent.PPATextField

@Preview(device = "spec:width=411.4dp,height=731.4dp")
@Composable
private fun RegisterScreenPreview() {
	PlanetPulseAcademyTheme(
		darkTheme = false
	) {
		RegisterScreenContent(
			state = RegisterState(),
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
		)
	}
}

@Composable
fun RegisterScreen(
	viewModel: RegisterViewModel,
	onNavigateUp: () -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BaseScreenWrapper(
		viewModel = viewModel,
		contentWindowInsets = WindowInsets.statusBars,
		onEvent = { event ->
			when (event) {
				// when register is successful, navigate to login screen
				is RegisterUiEvent.RegisterSuccess -> {
					onNavigateUp()
				}
			}
		}
	) { scaffoldPadding ->
		RegisterScreenContent(
			state = state,
			onPasswordVisibilityChanged = viewModel::showPassword,
			onNameChanged = viewModel::setName,
			onEmailChanged = viewModel::setEmail,
			onUsernameChanged = viewModel::setUsername,
			onPasswordChanged = viewModel::setPassword,
			onRegisterClicked = viewModel::register,
			onLoginClicked = onNavigateUp,
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
				.padding(scaffoldPadding)
		)
	}

}

@OptIn(
	ExperimentalLayoutApi::class,
	ExperimentalMaterial3Api::class
)
@Composable
private fun RegisterScreenContent(
	state: RegisterState,
	modifier: Modifier = Modifier,
	onPasswordVisibilityChanged: (Boolean) -> Unit = {},
	onNameChanged: (String) -> Unit = {},
	onEmailChanged: (String) -> Unit = {},
	onUsernameChanged: (String) -> Unit = {},
	onPasswordChanged: (String) -> Unit = {},
	onRegisterClicked: () -> Unit = {},
	onLoginClicked: () -> Unit = {},
) {

	val focusManager = LocalFocusManager.current

	val constraintSet = ConstraintSet {
		val (
			ppaBanner,
			welcomeText,
			middleContent,
			bottomContent,
		) = createRefsFor(
			"ppaBanner",
			"welcomeText",
			"middleContent",
			"bottomContent",
		)

		val topGuideline = createGuidelineFromTop(1f/4.5f)

		constrain(ppaBanner) {
			top.linkTo(parent.top)
			bottom.linkTo(welcomeText.top)

			centerHorizontallyTo(parent)
		}

		constrain(welcomeText) {
			top.linkTo(ppaBanner.bottom)
			bottom.linkTo(topGuideline)

			centerHorizontallyTo(parent)
		}

		constrain(middleContent) {
			top.linkTo(topGuideline)

			centerHorizontallyTo(parent)
		}

		constrain(bottomContent) {
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
					color = PPATheme.colorScheme.primary,
					modifier = Modifier
						.size(32.dp)
				)
			}
		}
	}

	ConstraintLayout(
		constraintSet = constraintSet,
		modifier = modifier
	) {
		Image(
			painter = painterResource(id = R.drawable.ppa_banner),
			contentDescription = null,
			modifier = Modifier
				.fillMaxWidth(0.48f)
				.layoutId("ppaBanner")
		)

		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			modifier = Modifier
				.layoutId("welcomeText")
		) {
			Text(
				text = stringResource(id = R.string.welcome),
				style = PPATheme.typography.titleLarge
			)

			Text(
				text = stringResource(id = R.string.please_sign_up_for_access_the_app),
				style = PPATheme.typography.bodyMedium
			)
		}

		MiddleContent(
			name = state.name,
			email = state.email,
			username = state.username,
			password = state.password,
			showPassword = state.showPassword,
			nameErrMsg = state.nameErrMsg,
			emailErrMsg = state.emailErrMsg,
			usernameErrMsg = state.usernameErrMsg,
			passwordErrMsg = state.passwordErrMsg,
			onPasswordVisibilityChanged = onPasswordVisibilityChanged,
			onNameChanged = onNameChanged,
			onEmailChanged = onEmailChanged,
			onUsernameChanged = onUsernameChanged,
			onPasswordChanged = onPasswordChanged,
			onRegisterClicked = onRegisterClicked,
			onLoginClicked = onLoginClicked,
			modifier = Modifier
				.fillMaxWidth(0.86f)
				.layoutId("middleContent")
		)

		Canvas(
			modifier = Modifier
				.fillMaxWidth()
				.height(24.dp)
				.clipToBounds()
				.layoutId("bottomContent")
		) {
			drawOval(
				color = Color.White,
				size = size.copy(height = size.height * 2),
				topLeft = Offset.Zero
			)
		}
	}

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MiddleContent(
	name: String,
	email: String,
	username: String,
	password: String,
	showPassword: Boolean,
	modifier: Modifier = Modifier,
	nameErrMsg: String? = null,
	emailErrMsg: String? = null,
	usernameErrMsg: String? = null,
	passwordErrMsg: String? = null,
	onPasswordVisibilityChanged: (Boolean) -> Unit = {},
	onNameChanged: (String) -> Unit = {},
	onEmailChanged: (String) -> Unit = {},
	onUsernameChanged: (String) -> Unit = {},
	onPasswordChanged: (String) -> Unit = {},
	onRegisterClicked: () -> Unit = {},
	onLoginClicked: () -> Unit = {},
) {

	val context = LocalContext.current
	val focusManager = LocalFocusManager.current
	val keyboardController = LocalSoftwareKeyboardController.current

	val rememberedOnNameChanged by rememberUpdatedState(onNameChanged)
	val rememberedOnEmailChanged by rememberUpdatedState(onEmailChanged)
	val rememberedOnUsernameChanged by rememberUpdatedState(onUsernameChanged)
	val rememberedOnPasswordChanged by rememberUpdatedState(onPasswordChanged)

	val (
		nameFocusRequester,
		emailFocusRequester,
		usernameFocusRequester,
		passwordFocusRequester,
	) = FocusRequester.createRefs()

	var nameTextFieldValue by remember {
		mutableStateOf(
			TextFieldValue(
				text = name
			)
		)
	}

	var emailTextFieldValue by remember {
		mutableStateOf(
			TextFieldValue(
				text = email
			)
		)
	}

	var usernameTextFieldValue by remember {
		mutableStateOf(
			TextFieldValue(
				text = username
			)
		)
	}

	var passwordTextFieldValue by remember {
		mutableStateOf(
			TextFieldValue(
				text = password
			)
		)
	}

	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = modifier
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier
				.clip(RoundedCornerShape(8))
				.background(PPATheme.colorScheme.onBackground)
				.padding(24.dp)
		) {
			Text(
				text = stringResource(id = R.string.sign_up),
				style = PPATheme.typography.titleLarge.copy(
					color = Color.Black
				)
			)

			Spacer(modifier = Modifier.height(8.dp))

			PPATextField(
				value = nameTextFieldValue,
				singleLine = true,
				isError = nameErrMsg != null,
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
					nameTextFieldValue = newValue.preventSelection(nameTextFieldValue)
					rememberedOnNameChanged(nameTextFieldValue.text)
				},
				placeholder = {
					Text(stringResource(id = R.string.name))
				},
				supportingText = if (nameErrMsg != null) {
					{
						Text(
							text = nameErrMsg.parse(context),
							style = LocalTextStyle.current.copy(
								color = MaterialTheme.colorScheme.onBackground
							)
						)
					}
				} else null,
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(nameFocusRequester)
			)

			PPATextField(
				value = emailTextFieldValue,
				singleLine = true,
				isError = emailErrMsg != null,
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
					emailTextFieldValue = newValue.preventSelection(emailTextFieldValue)
					rememberedOnEmailChanged(emailTextFieldValue.text)
				},
				placeholder = {
					Text(stringResource(id = R.string.email))
				},
				supportingText = if (emailErrMsg != null) {
					{
						Text(
							text = emailErrMsg.parse(context),
							style = LocalTextStyle.current.copy(
								color = MaterialTheme.colorScheme.onBackground
							)
						)
					}
				} else null,
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(emailFocusRequester)
			)

			PPATextField(
				value = usernameTextFieldValue,
				singleLine = true,
				isError = usernameErrMsg != null,
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
					usernameTextFieldValue = newValue.preventSelection(usernameTextFieldValue)
					rememberedOnUsernameChanged(usernameTextFieldValue.text)
				},
				placeholder = {
					Text(stringResource(id = R.string.username))
				},
				supportingText = if (usernameErrMsg != null) {
					{
						Text(
							text = usernameErrMsg.parse(context),
							style = LocalTextStyle.current.copy(
								color = MaterialTheme.colorScheme.error
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
				isError = passwordErrMsg != null,
				visualTransformation = if (showPassword) VisualTransformation.None
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
					passwordTextFieldValue = newValue.preventSelection(passwordTextFieldValue)
					rememberedOnPasswordChanged(passwordTextFieldValue.text)
				},
				placeholder = {
					Text(stringResource(id = R.string.password))
				},
				supportingText = if (passwordErrMsg != null) {
					{
						Text(
							text = passwordErrMsg.parse(context),
							style = LocalTextStyle.current.copy(
								color = MaterialTheme.colorScheme.error
							)
						)
					}
				} else null,
				trailingIcon = {
					IconButton(
						onClick = {
							onPasswordVisibilityChanged(!showPassword)
						}
					) {
						AnimatedContent(
							label = "show_password_button",
							targetState = showPassword
						) { show ->
							Icon(
								painter = painterResource(
									id = if (show) R.drawable.ic_eye
									else R.drawable.ic_eye_slash
								),
								contentDescription = null,
								tint = PPATheme.colorScheme.inverseOnBackground.copy(alpha = 0.7f)
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
				onClick = onRegisterClicked,
				colors = ButtonDefaults.buttonColors(
					containerColor = PPATheme.colorScheme.button,
					contentColor = PPATheme.colorScheme.onPrimary
				),
				modifier = Modifier
					.fillMaxWidth()
			) {
				Text(stringResource(id = R.string.sign_in))
			}

			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Text(
					text = stringResource(id = R.string.already_have_an_account) + "?",
					style = PPATheme.typography.bodyMedium.copy(
						color = Color.Black
					)
				)

				ClickableText(
					text = buildAnnotatedString {
						append(stringResource(id = R.string.sign_in))
					},
					style = MaterialTheme.typography.bodyMedium.copy(
						color = PPATheme.colorScheme.button
					),
					onClick = {
						onLoginClicked()
					}
				)
			}
		}

		Text(
			text = stringResource(id = R.string.or),
			style = PPATheme.typography.bodyMedium
		)

		Button(
			shape = MaterialTheme.shapes.medium,
			contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
			colors = ButtonDefaults.buttonColors(
				containerColor = PPATheme.colorScheme.onBackground
			),
			onClick = {
				// TODO: Sign in with googel
			}
		) {
			Image(
				painter = painterResource(id = R.drawable.ic_google),
				contentDescription = null,
				modifier = Modifier
					.size(ButtonDefaults.IconSize)
			)

			Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))

			Text(
				text = stringResource(id = R.string.sign_in_with_google),
				style = LocalTextStyle.current.copy(
					color = PPATheme.colorScheme.inverseOnBackground
				)
			)
		}
	}
}
