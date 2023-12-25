package com.ufovanguard.planetpulseacademy.ui.register

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize

@Parcelize
@Stable
data class RegisterState(
	val email: String = "",
	val username: String = "",
	val password: String = "",
	val emailErrMsg: String? = null,
	val usernameErrMsg: String? = null,
	val passwordErrMsg: String? = null,
	val showPassword: Boolean = false
): Parcelable
