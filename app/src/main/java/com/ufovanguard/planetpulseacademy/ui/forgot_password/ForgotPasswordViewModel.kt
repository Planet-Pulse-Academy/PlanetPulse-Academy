package com.ufovanguard.planetpulseacademy.ui.forgot_password

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ufovanguard.planetpulseacademy.R
import com.ufovanguard.planetpulseacademy.data.model.remote.body.ForgotPasswordBody
import com.ufovanguard.planetpulseacademy.data.model.remote.body.ResetPasswordBody
import com.ufovanguard.planetpulseacademy.data.model.remote.body.VerifyOtpBody
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import com.ufovanguard.planetpulseacademy.foundation.base.ui.UiEvent
import com.ufovanguard.planetpulseacademy.foundation.common.validator.EmailValidator
import com.ufovanguard.planetpulseacademy.foundation.common.validator.OtpValidator
import com.ufovanguard.planetpulseacademy.foundation.common.validator.PasswordConfirmValidator
import com.ufovanguard.planetpulseacademy.foundation.common.validator.PasswordValidator
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
class ForgotPasswordViewModel @Inject constructor(
	private val workManager: WorkManager,
	savedStateHandle: SavedStateHandle
): BaseViewModel<ForgotPasswordState>(
	defaultState = ForgotPasswordState(),
	savedStateHandle = savedStateHandle
) {

	private val _currentRequestOtpWorkId = Channel<UUID?>()
	private val currentRequestOtpWorkId: Flow<UUID?> = _currentRequestOtpWorkId.receiveAsFlow()

	private val _currentVerifyOtpWorkId = Channel<UUID?>()
	private val currentVerifyOtpWorkId: Flow<UUID?> = _currentVerifyOtpWorkId.receiveAsFlow()

	private val _currentResetPasswordWorkId = Channel<UUID?>()
	private val currentResetPasswordWorkId: Flow<UUID?> = _currentResetPasswordWorkId.receiveAsFlow()

	init {
		viewModelScope.launch {
			currentRequestOtpWorkId.filterNotNull().flatMapLatest { uuid ->
				workManager.getWorkInfoByIdFlow(uuid)
			}.collectLatest { workInfo ->
				updateState {
					when (workInfo.state) {
						WorkInfo.State.SUCCEEDED -> {
							sendEvent(ForgotPasswordUiEvent.OtpHasBeenSent(UiEvent.asStringResource(R.string.otp_has_been_sent_to_your_email)))

							copy(
								isLoading = false,
								isSendOtpButtonEnabled = false
							)
						}
						WorkInfo.State.FAILED -> {
							sendEvent(
								ForgotPasswordUiEvent.FailedToSendOtp(
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

		viewModelScope.launch {
			currentVerifyOtpWorkId.filterNotNull().flatMapLatest { uuid ->
				workManager.getWorkInfoByIdFlow(uuid)
			}.collectLatest { workInfo ->
				updateState {
					when (workInfo.state) {
						WorkInfo.State.SUCCEEDED -> {
							copy(
								isOtpVerified = true
							)
						}
						WorkInfo.State.FAILED -> {
							sendEvent(
								ForgotPasswordUiEvent.FailedToSendOtp(
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

		viewModelScope.launch {
			currentResetPasswordWorkId.filterNotNull().flatMapLatest { uuid ->
				workManager.getWorkInfoByIdFlow(uuid)
			}.collectLatest { workInfo ->
				updateState {
					when (workInfo.state) {
						WorkInfo.State.SUCCEEDED -> {
							sendEvent(ForgotPasswordUiEvent.PasswordResetSuccessful(UiEvent.asStringResource(R.string.password_reset_successful)))

							copy(
								isLoading = false
							)
						}
						WorkInfo.State.FAILED -> {
							sendEvent(
								ForgotPasswordUiEvent.FailedToSendOtp(
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

	fun setOtp(otp: String) {
		updateState {
			copy(
				otp = otp
			)
		}
	}

	fun setEmail(email: String) {
		updateState {
			copy(
				email = email,
				emailErrMsg = null
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

	fun setPasswordConfirm(passwordConfirm: String) {
		updateState {
			copy(
				passwordConfirm = passwordConfirm,
				passwordConfirmErrMsg = null
			)
		}
	}

	fun setOtpButtonEnabled(enabled: Boolean) {
		updateState {
			copy(
				isSendOtpButtonEnabled = enabled
			)
		}
	}

	fun validate(onSuccess: (otp: String, email: String, password: String, passwordConfirm: String) -> Unit) {
		val passwordConfirm = state.value.passwordConfirm.trim()
		val password = state.value.password.trim()
		val email = state.value.email.trim()
		val otp = state.value.otp.trim()

		val otpValidatorResult = OtpValidator().validate(email)
		val emailValidatorResult = EmailValidator().validate(email)
		val passwordValidatorResult = PasswordValidator().validate(password)
		val passwordConfirmValidatorResult = PasswordConfirmValidator(password).validate(passwordConfirm)

		updateState {
			copy(
				email = email,
				password = password,
				passwordConfirm = passwordConfirm,
				otpErrMsg = otpValidatorResult.errMsg,
				emailErrMsg = emailValidatorResult.errMsg,
				passwordErrMsg = passwordValidatorResult.errMsg,
				passwordConfirmErrMsg = passwordConfirmValidatorResult.errMsg
			)
		}

		if (
			emailValidatorResult.isSuccess &&
			passwordValidatorResult.isSuccess &&
			passwordConfirmValidatorResult.isSuccess &&
			otpValidatorResult.isSuccess
		) {
			onSuccess(otp, email, password, passwordConfirm)
		}
	}

	fun requestOtp() {
		validate { _, email, _, _ ->
			viewModelScope.launch {
				updateState {
					copy(
						isLoading = true
					)
				}

				workManager.enqueue(
					Workers.forgotPasswordWorker(
						ForgotPasswordBody(email)
					).also { _currentRequestOtpWorkId.send(it.id) }
				)
			}
		}
	}

	fun confirm() {
		validate { otp, email, password, _ ->
			viewModelScope.launch {
				updateState {
					copy(
						isLoading = true
					)
				}

				workManager.beginWith(
					Workers.verifyOtp(
						email = email,
						verifyOtpBody = VerifyOtpBody(otp)
					).also { _currentVerifyOtpWorkId.send(it.id) }
				).then(
					Workers.resetPassword(
						code = otp,
						resetPasswordBody = ResetPasswordBody(
							email = email,
							password = password
						)
					).also { _currentResetPasswordWorkId.send(it.id) }
				).enqueue()
			}
		}
	}

}