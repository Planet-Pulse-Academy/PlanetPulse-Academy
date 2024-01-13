package com.ufovanguard.planetpulseacademy.ui.forgot_password

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.minimumInteractiveComponentSize
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
import com.ufovanguard.planetpulseacademy.foundation.common.Timer
import com.ufovanguard.planetpulseacademy.foundation.common.validator.ValidatorResult.Success.parse
import com.ufovanguard.planetpulseacademy.foundation.extension.preventSelection
import com.ufovanguard.planetpulseacademy.foundation.theme.PPATheme
import com.ufovanguard.planetpulseacademy.foundation.theme.PlanetPulseAcademyTheme
import com.ufovanguard.planetpulseacademy.foundation.uicomponent.AnimatedTextByChar
import com.ufovanguard.planetpulseacademy.foundation.uicomponent.PPATextField
import kotlin.time.Duration.Companion.seconds

@Preview
@Composable
private fun ForgotPasswordScreenPreview() {
	PlanetPulseAcademyTheme {
		ForgotPasswordScreenContent(
			state = ForgotPasswordState(),
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
		)
	}
}

@Composable
fun ForgotPasswordScreen(
	viewModel: ForgotPasswordViewModel,
	onNavigateUp: () -> Unit
) {

	val state by viewModel.state.collectAsStateWithLifecycle()

	BackHandler(enabled = !state.isEmailMode) {
		viewModel.setIsEmailMode(true)
	}

	BaseScreenWrapper(
		viewModel = viewModel,
		contentWindowInsets = WindowInsets.statusBars,
		onEvent = { event ->
			when (event) {
				is ForgotPasswordUiEvent.PasswordResetSuccessful -> {
					onNavigateUp()
				}
			}
		}
	) { scaffoldPadding ->
		ForgotPasswordScreenContent(
			state = state,
			onOtpChanged = viewModel::setOtp,
			onEmailChanged = viewModel::setEmail,
			onPasswordChanged = viewModel::setPassword,
			onPasswordConfirmChanged = viewModel::setPasswordConfirm,
			onPasswordVisibilityChanged = viewModel::showPassword,
			onConfirmClicked = viewModel::confirm,
			onSendOtp = viewModel::requestOtp,
			onOtpButtonEnabled = {
				viewModel.setOtpButtonEnabled(true)
			},
			modifier = Modifier
				.fillMaxSize()
				.background(PPATheme.colorScheme.background)
				.padding(scaffoldPadding)
		)
	}

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun ForgotPasswordScreenContent(
	state: ForgotPasswordState,
	modifier: Modifier = Modifier,
	onPasswordVisibilityChanged: (Boolean) -> Unit = {},
	onOtpChanged: (String) -> Unit = {},
	onEmailChanged: (String) -> Unit = {},
	onPasswordChanged: (String) -> Unit = {},
	onPasswordConfirmChanged: (String) -> Unit = {},
	onOtpButtonEnabled: () -> Unit = {},
	onConfirmClicked: () -> Unit = {},
	onSendOtp: () -> Unit = {},
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
			otp = state.otp,
			email = state.email,
			password = state.password,
			passwordConfirm = state.passwordConfirm,
			showPassword = state.showPassword,
			emailErrMsg = state.emailErrMsg,
			passwordErrMsg = state.passwordErrMsg,
			passwordConfirmErrMsg = state.passwordConfirmErrMsg,
			isEmailMode = state.isEmailMode,
			isSendOtpButtonEnabled = state.isSendOtpButtonEnabled,
			onPasswordVisibilityChanged = onPasswordVisibilityChanged,
			onOtpChanged = onOtpChanged,
			onEmailChanged = onEmailChanged,
			onPasswordChanged = onPasswordChanged,
			onPasswordConfirmChanged = onPasswordConfirmChanged,
			onOtpButtonEnabled = onOtpButtonEnabled,
			onConfirmClicked = onConfirmClicked,
			onSendOtp = onSendOtp,
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
	otp: String,
	email: String,
	password: String,
	passwordConfirm: String,
	showPassword: Boolean,
	isEmailMode: Boolean,
	isSendOtpButtonEnabled: Boolean,
	modifier: Modifier = Modifier,
	emailErrMsg: String? = null,
	passwordErrMsg: String? = null,
	passwordConfirmErrMsg: String? = null,
	onPasswordVisibilityChanged: (Boolean) -> Unit = {},
	onOtpChanged: (String) -> Unit = {},
	onEmailChanged: (String) -> Unit = {},
	onPasswordChanged: (String) -> Unit = {},
	onPasswordConfirmChanged: (String) -> Unit = {},
	onOtpButtonEnabled: () -> Unit,
	onConfirmClicked: () -> Unit = {},
	onSendOtp: () -> Unit = {},
) {

	val (
		otpFocusRequester,
		emailFocusRequester,
		passwordFocusRequester,
		passwordConfirmFocusRequester,
	) = FocusRequester.createRefs()

	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = modifier
			.clip(RoundedCornerShape(8))
			.background(PPATheme.colorScheme.onBackground)
			.padding(24.dp)
	) {
		Text(
			text = stringResource(id = R.string.forgot_password),
			style = PPATheme.typography.titleLarge.copy(
				color = Color.Black
			)
		)

		Spacer(modifier = Modifier.height(8.dp))

		if (isEmailMode) {
			EmailTextField(
				email = email,
				emailErrMsg = emailErrMsg,
				onEmailChanged = onEmailChanged,
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(emailFocusRequester)
			)
		} else {
			OtpTextField(
				otp = otp,
				isSendOtpButtonEnabled = isSendOtpButtonEnabled,
				onOtpButtonEnabled = onOtpButtonEnabled,
				onOtpChanged = onOtpChanged,
				onSendOtp = onSendOtp,
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(otpFocusRequester)
			)

			PasswordTextField(
				password = password,
				passwordErrMsg = passwordErrMsg,
				showPassword = showPassword,
				onPasswordVisibilityChanged = onPasswordVisibilityChanged,
				onPasswordChanged = onPasswordChanged,
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(passwordFocusRequester)
			)

			PasswordConfirmTextField(
				passwordConfirm = passwordConfirm,
				passwordConfirmErrMsg = passwordConfirmErrMsg,
				showPassword = showPassword,
				onPasswordVisibilityChanged = onPasswordVisibilityChanged,
				onPasswordConfirmChanged = onPasswordConfirmChanged,
				modifier = Modifier
					.fillMaxWidth()
					.focusRequester(passwordConfirmFocusRequester)
			)
		}

		Button(
			shape = MaterialTheme.shapes.medium,
			onClick = onConfirmClicked,
			colors = ButtonDefaults.buttonColors(
				containerColor = PPATheme.colorScheme.button,
				contentColor = PPATheme.colorScheme.onPrimary
			),
			modifier = Modifier
				.fillMaxWidth()
		) {
			Text(stringResource(id = R.string.confirm))
		}
	}
}

@Composable
private fun EmailTextField(
	email: String,
	emailErrMsg: String?,
	onEmailChanged: (String) -> Unit,
	modifier: Modifier = Modifier
) {

	val context = LocalContext.current
	val focusManager = LocalFocusManager.current

	val rememberedOnEmailChanged by rememberUpdatedState(onEmailChanged)

	var emailTextFieldValue by remember {
		mutableStateOf(
			TextFieldValue(
				text = email
			)
		)
	}

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
		modifier = modifier
	)
}

@Composable
private fun OtpTextField(
	otp: String,
	isSendOtpButtonEnabled: Boolean,
	modifier: Modifier = Modifier,
	onOtpButtonEnabled: () -> Unit,
	onOtpChanged: (String) -> Unit,
	onSendOtp: () -> Unit
) {

	val focusManager = LocalFocusManager.current


	val timer = remember { Timer() }

	val isTimerRunning by timer.isRunning.collectAsStateWithLifecycle()
	val remainingTimeInMilliseconds by timer.remainingTimeInMilliseconds.collectAsStateWithLifecycle()

	val rememberedOnOtpChanged by rememberUpdatedState(onOtpChanged)

	var otpTextFieldValue by remember {
		mutableStateOf(
			TextFieldValue(
				text = otp
			)
		)
	}

	LaunchedEffect(isSendOtpButtonEnabled) {
		if (!isSendOtpButtonEnabled) timer.postDelayed(
			duration = 60.seconds
		) { onOtpButtonEnabled() }
	}

	PPATextField(
		value = otpTextFieldValue,
		singleLine = true,
		keyboardOptions = KeyboardOptions(
			imeAction = ImeAction.Next,
			keyboardType = KeyboardType.Number
		),
		keyboardActions = KeyboardActions(
			onNext = {
				focusManager.moveFocus(FocusDirection.Next)
			}
		),
		onValueChange = { newValue ->
			otpTextFieldValue = newValue.copy(
				text = newValue.text.filter { it.isDigit() }
			).preventSelection(otpTextFieldValue)

			rememberedOnOtpChanged(otpTextFieldValue.text)
		},
		placeholder = {
			Text(stringResource(id = R.string.otp))
		},
		trailingIcon = {
			AnimatedContent(
				label = "otp_trailing_icon",
				targetState = isTimerRunning,
				transitionSpec = {
					scaleIn() togetherWith scaleOut()
				}
			) { isRunning ->
				Box(
					contentAlignment = Alignment.Center,
					modifier = Modifier
						.minimumInteractiveComponentSize()
				) {
					if (isRunning) {
						AnimatedTextByChar(
							text = (remainingTimeInMilliseconds / 1000).toString(),
							style = PPATheme.typography.bodyMedium.copy(
								color = PPATheme.colorScheme.inverseOnBackground.copy(alpha = 0.7f)
							)
						)
					} else {
						IconButton(onClick = onSendOtp) {
							Icon(
								painter = painterResource(id = R.drawable.ic_send),
								contentDescription = null,
								tint = PPATheme.colorScheme.inverseOnBackground.copy(alpha = 0.7f)
							)
						}
					}
				}
			}
		},
		modifier = modifier
	)
}

@Composable
private fun PasswordTextField(
	password: String,
	passwordErrMsg: String?,
	showPassword: Boolean,
	onPasswordVisibilityChanged: (Boolean) -> Unit,
	onPasswordChanged: (String) -> Unit,
	modifier: Modifier = Modifier
) {

	val context = LocalContext.current
	val focusManager = LocalFocusManager.current

	val rememberedOnPasswordChanged by rememberUpdatedState(onPasswordChanged)

	var passwordTextFieldValue by remember {
		mutableStateOf(
			TextFieldValue(
				text = password
			)
		)
	}

	PPATextField(
		value = passwordTextFieldValue,
		singleLine = true,
		isError = passwordErrMsg != null,
		visualTransformation = if (showPassword) VisualTransformation.None
		else PasswordVisualTransformation(),
		keyboardOptions = KeyboardOptions(
			imeAction = ImeAction.Next,
			keyboardType = KeyboardType.Password
		),
		keyboardActions = KeyboardActions(
			onNext = {
				focusManager.moveFocus(FocusDirection.Next)
			}
		),
		onValueChange = { newValue ->
			passwordTextFieldValue = newValue.preventSelection(passwordTextFieldValue)
			rememberedOnPasswordChanged(passwordTextFieldValue.text)
		},
		placeholder = {
			Text(stringResource(id = R.string.new_password))
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
		modifier = modifier
	)
}

@Composable
private fun PasswordConfirmTextField(
	passwordConfirm: String,
	passwordConfirmErrMsg: String?,
	showPassword: Boolean,
	onPasswordVisibilityChanged: (Boolean) -> Unit,
	onPasswordConfirmChanged: (String) -> Unit,
	modifier: Modifier = Modifier
) {

	val context = LocalContext.current
	val keyboardController = LocalSoftwareKeyboardController.current

	val rememberedOnPasswordConfirmChanged by rememberUpdatedState(onPasswordConfirmChanged)

	var passwordConfirmTextFieldValue by remember {
		mutableStateOf(
			TextFieldValue(
				text = passwordConfirm
			)
		)
	}

	PPATextField(
		value = passwordConfirmTextFieldValue,
		singleLine = true,
		isError = passwordConfirmErrMsg != null,
		visualTransformation = if (showPassword) VisualTransformation.None
		else PasswordVisualTransformation(),
		onValueChange = { newValue ->
			passwordConfirmTextFieldValue = newValue.preventSelection(passwordConfirmTextFieldValue)
			rememberedOnPasswordConfirmChanged(passwordConfirmTextFieldValue.text)
		},
		keyboardOptions = KeyboardOptions(
			imeAction = ImeAction.Done,
			keyboardType = KeyboardType.Password
		),
		keyboardActions = KeyboardActions(
			onDone = {
				keyboardController?.hide()
			}
		),
		placeholder = {
			Text(stringResource(id = R.string.confirm_password))
		},
		supportingText = if (passwordConfirmErrMsg != null) {
			{
				Text(
					text = passwordConfirmErrMsg.parse(context),
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
		modifier = modifier
	)
}
