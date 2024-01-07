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
	val isSendOtpButtonEnabled: Boolean = true,
	val isOtpVerified: Boolean = false,
	val showPassword: Boolean = false,
	val isLoading: Boolean = false
): Parcelable
