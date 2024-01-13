package com.ufovanguard.planetpulseacademy.ui.forgot_password

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class ForgotPasswordState(
	val otp: String = "",
	val email: String = "",
	val password: String = "",
	val passwordConfirm: String = "",
	val otpErrMsg: String? = null,
	val emailErrMsg: String? = null,
	val passwordErrMsg: String? = null,
	val passwordConfirmErrMsg: String? = null,
	val showPassword: Boolean = false,
	val isLoading: Boolean = false,
	/**
	 * When this variable is `true`, only show email text field and confirmation button.
	 *
	 * When user click the button, set this variable to `false` and hide email text field, and then
	 * show otp text field, password text field, and confirm password text field.
	 *
	 * But if user click `back button` and this variable is `false`, set this variable to `true`.
	 * if user click `back button` again, popup to previous navigation.
	 */
	val isEmailMode: Boolean = true
): Parcelable
