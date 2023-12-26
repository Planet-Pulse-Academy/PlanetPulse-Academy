package com.ufovanguard.planetpulseacademy.ui.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.data.model.remote.body.RegisterBody
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import com.ufovanguard.planetpulseacademy.foundation.base.ui.UiEvent
import com.ufovanguard.planetpulseacademy.foundation.common.EmailValidator
import com.ufovanguard.planetpulseacademy.foundation.common.NameValidator
import com.ufovanguard.planetpulseacademy.foundation.common.PasswordValidator
import com.ufovanguard.planetpulseacademy.foundation.common.UsernameValidator
import com.ufovanguard.planetpulseacademy.foundation.worker.RegisterWorker
import com.ufovanguard.planetpulseacademy.foundation.worker.Workers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RegisterViewModel @Inject constructor(
	private val workManager: WorkManager,
	savedStateHandle: SavedStateHandle
): BaseViewModel<RegisterState>(
	defaultState = RegisterState(),
	savedStateHandle = savedStateHandle
) {

	private val _currentRegisterWorkId = Channel<UUID?>()
	private val currentRegisterWorkId: Flow<UUID?> = _currentRegisterWorkId.receiveAsFlow()

	init {
		viewModelScope.launch {
			currentRegisterWorkId.filterNotNull().flatMapLatest { uuid ->
				workManager.getWorkInfoByIdFlow(uuid)
			}.collectLatest { workInfo ->
				updateState {
					when (workInfo.state) {
						WorkInfo.State.SUCCEEDED -> {
							sendEvent(RegisterUiEvent.RegisterSuccess(UiEvent.asStringResource(R.string.email_verification_has_been_sent)))

							copy(isLoading = false)
						}
						WorkInfo.State.FAILED -> {
							sendEvent(
								RegisterUiEvent.RegisterFailed(
									workInfo.outputData.getString(RegisterWorker.EXTRA_ERROR_MESSAGE)
										?: "Unknown error"
								)
							)

							copy(isLoading = false)
						}
						else -> this
					}
				}
			}
		}
	}

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

	fun setName(name: String) {
		updateState {
			copy(
				name = name
			)
		}
	}

	fun register() = viewModelScope.launch {
		val passwordValidatorResult = PasswordValidator().validate(state.value.password)
		val usernameValidatorResult = UsernameValidator().validate(state.value.username)
		val emailValidatorResult = EmailValidator().validate(state.value.email)
		val nameValidatorResult = NameValidator().validate(state.value.name)

		if (
			passwordValidatorResult.isSuccess &&
			usernameValidatorResult.isSuccess &&
			emailValidatorResult.isSuccess &&
			nameValidatorResult.isSuccess
		) {
			updateState {
				copy(
					isLoading = true
				)
			}

			workManager.enqueue(
				Workers.registerWorker(
					RegisterBody(
						username = state.value.username.trim(),
						password = state.value.password.trim(),
						email = state.value.email.trim(),
						name = state.value.name.trim()
					)
				).also {
					_currentRegisterWorkId.send(it.id)
				}
			)
		}

		updateState {
			copy(
				passwordErrMsg = passwordValidatorResult.errMsg,
				usernameErrMsg = usernameValidatorResult.errMsg,
				emailErrMsg = emailValidatorResult.errMsg,
				nameErrMsg = nameValidatorResult.errMsg
			)
		}
	}

}