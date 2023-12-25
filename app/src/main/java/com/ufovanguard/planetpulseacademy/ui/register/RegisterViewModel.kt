package com.ufovanguard.planetpulseacademy.ui.register

import androidx.lifecycle.SavedStateHandle
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import com.ufovanguard.planetpulseacademy.foundation.common.EmailValidator
import com.ufovanguard.planetpulseacademy.foundation.common.PasswordValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle
): BaseViewModel<RegisterState>(
	defaultState = RegisterState(),
	savedStateHandle = savedStateHandle
) {

	fun showPassword(show: Boolean) {
		updateState {
			copy(
				showPassword = show
			)
		}
	}

	fun setUsername(username: String) {
		updateState {
			copy(
				username = username
			)
		}
	}

	fun setPassword(password: String) {
		updateState {
			copy(
				password = password
			)
		}
	}

	fun setEmail(email: String) {
		updateState {
			copy(
				email = email
			)
		}
	}

	fun register() {
		val passwordValidatorResult = PasswordValidator().validate(state.value.password)
		val emailValidatorResult = EmailValidator().validate(state.value.password)

		if (passwordValidatorResult.isSuccess && emailValidatorResult.isSuccess) {
			sendEvent(RegisterUiEvent.RegisterSuccess)
		}

		updateState {
			copy(
				passwordErrMsg = passwordValidatorResult.errMsg,
				emailErrMsg = emailValidatorResult.errMsg
			)
		}
	}

}