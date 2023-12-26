package com.ufovanguard.planetpulseacademy.ui.login

import com.ufovanguard.planetpulseacademy.foundation.base.ui.UiEvent

object LoginUiEvent {

	data class LoginFailed(override val message: String): UiEvent.ShowToast(message)

	data object LoginSuccess: UiEvent()

}