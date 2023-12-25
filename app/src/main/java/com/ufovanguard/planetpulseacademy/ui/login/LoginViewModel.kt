package com.ufovanguard.planetpulseacademy.ui.login

import androidx.lifecycle.SavedStateHandle
import com.ufovanguard.planetpulseacademy.data.repository.UserCredentialRepository
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import com.ufovanguard.planetpulseacademy.foundation.common.PasswordValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val userCredentialRepository: UserCredentialRepository,
	savedStateHandle: SavedStateHandle
): BaseViewModel<LoginState>(
	defaultState = LoginState(),
	savedStateHandle = savedStateHandle
) {

	fun setUsername(username: String) {
		updateState { copy(username = username) }
	}

	fun setPassword(password: String) {
		updateState { copy(password = password) }
	}

	fun login() {
		val passwordValidatorResult = PasswordValidator().validate(state.value.password)

		if (passwordValidatorResult.isSuccess) {
			sendEvent(LoginUiEvent.LoginSuccess)
		}

		updateState {
			copy(
				passwordErrMsg = passwordValidatorResult.errMsg
			)
		}
	}

}