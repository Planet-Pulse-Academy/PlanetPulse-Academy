package com.ufovanguard.planetpulseacademy.foundation.base.ui

import com.ufovanguard.planetpulseacademy.foundation.base.ui.UiEvent

/**
 * Result dari [UiEvent]
 *
 * Misalnya user mengklik dismiss action atau action di snackbar,
 * maka [BaseViewModel.uiEventResult] akan menginvoke [Flow.collect] jika tersedia
 *
 * @author kafri8889
 */
sealed class UiEventResult {

	/**
	 * @param [UiEvent] sent, can be used as ID
	 */
	data class ActionPerformed(val event: UiEvent): UiEventResult()

	/**
	 * @param [UiEvent] sent, can be used as ID
	 */
	data class Dismissed(val event: UiEvent): UiEventResult()

}