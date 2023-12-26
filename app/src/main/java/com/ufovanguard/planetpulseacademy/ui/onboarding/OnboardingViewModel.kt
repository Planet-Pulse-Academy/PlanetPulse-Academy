package com.ufovanguard.planetpulseacademy.ui.onboarding

import androidx.lifecycle.SavedStateHandle
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle
): BaseViewModel<OnboardingState>(
	defaultState = OnboardingState(),
	savedStateHandle = savedStateHandle
) {

	fun next() {
		updateState {
			copy(
				page = page + 1
			)
		}
	}

	fun previous() {
		updateState {
			copy(
				page = page - 1
			)
		}
	}

}