package com.ufovanguard.planetpulseacademy.ui.register

import android.widget.Toast
import com.ufovanguard.planetpulseacademy.foundation.base.ui.UiEvent

object RegisterUiEvent {

	data class RegisterFailed(override val message: String): UiEvent.ShowToast(message)

	data class RegisterSuccess(override val message: String): UiEvent.ShowToast(message, length = Toast.LENGTH_LONG)

}