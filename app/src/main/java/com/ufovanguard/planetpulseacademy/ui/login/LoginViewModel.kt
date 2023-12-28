package com.ufovanguard.planetpulseacademy.ui.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ufovanguard.planetpulseacademy.data.model.remote.body.LoginBody
import com.ufovanguard.planetpulseacademy.data.repository.UserCredentialRepository
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import com.ufovanguard.planetpulseacademy.foundation.common.validator.PasswordValidator
import com.ufovanguard.planetpulseacademy.foundation.common.validator.UsernameValidator
import com.ufovanguard.planetpulseacademy.foundation.worker.LoginWorker
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
class LoginViewModel @Inject constructor(
	private val userCredentialRepository: UserCredentialRepository,
	private val workManager: WorkManager,
	savedStateHandle: SavedStateHandle
): BaseViewModel<LoginState>(
	defaultState = LoginState(),
	savedStateHandle = savedStateHandle
) {

	private val _currentLoginWorkId = Channel<UUID?>()
	private val currentLoginWorkId: Flow<UUID?> = _currentLoginWorkId.receiveAsFlow()

	init {
	    viewModelScope.launch {
			currentLoginWorkId.filterNotNull().flatMapLatest { uuid ->
				workManager.getWorkInfoByIdFlow(uuid)
			}.collectLatest { workInfo ->
				updateState {
					when (workInfo.state) {
						WorkInfo.State.SUCCEEDED -> {
							sendEvent(LoginUiEvent.LoginSuccess)

							copy(isLoading = false)
						}
						WorkInfo.State.FAILED -> {
							sendEvent(
								LoginUiEvent.LoginFailed(
									workInfo.outputData.getString(LoginWorker.EXTRA_ERROR_MESSAGE)
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
				username = username,
				usernameErrMsg = null
			)
		}
	}

	fun setPassword(password: String) {
		updateState {
			copy(
				password = password,
				passwordErrMsg = null
			)
		}
	}

	fun login() = viewModelScope.launch {
		val password = state.value.password.trim()
		val username = state.value.username.trim()

		val passwordValidatorResult = PasswordValidator().validate(password)
		val usernameValidatorResult = UsernameValidator().validate(username)

		updateState {
			copy(
				password = password,
				username = username,
				passwordErrMsg = passwordValidatorResult.errMsg,
				usernameErrMsg = usernameValidatorResult.errMsg
			)
		}

		if (passwordValidatorResult.isSuccess && usernameValidatorResult.isSuccess) {
			updateState {
				copy(
					isLoading = true
				)
			}

			workManager.enqueue(
				Workers.loginWorker(
					LoginBody(
						username = username,
						password = password
					)
				).also {
					_currentLoginWorkId.send(it.id)
				}
			)
		}
	}

}