package com.ufovanguard.planetpulseacademy.ui.login

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize

@Parcelize
@Stable
data class LoginState(
	val username: String = "",
	val password: String = "",
	val usernameErrMsg: String? = null,
	val passwordErrMsg: String? = null,
): Parcelable
