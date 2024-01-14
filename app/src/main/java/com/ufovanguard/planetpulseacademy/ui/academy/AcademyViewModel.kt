package com.ufovanguard.planetpulseacademy.ui.academy

import androidx.lifecycle.SavedStateHandle
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AcademyViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle
): BaseViewModel<AcademyState>(
	defaultState = AcademyState(),
	savedStateHandle = savedStateHandle
) {

	fun setSearchQuery(q: String) {
		updateState {
			copy(
				searchQuery = q
			)
		}
	}

	fun search() {

	}

}