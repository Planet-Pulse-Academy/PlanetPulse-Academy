package com.ufovanguard.planetpulseacademy.ui.forgot_password

import android.widget.Toast
import com.ufovanguard.planetpulseacademy.foundation.base.ui.UiEvent

object ForgotPasswordUiEvent {

	data class OtpHasBeenSent(
		override val message: String,
	): UiEvent.ShowToast(message, Toast.LENGTH_LONG)

	data class FailedToSendOtp(
		override val message: String
	): UiEvent.ShowToast(message, Toast.LENGTH_LONG)

	data class PasswordResetSuccessful(
		override val message: String
	): UiEvent.ShowToast(message, Toast.LENGTH_SHORT)

}