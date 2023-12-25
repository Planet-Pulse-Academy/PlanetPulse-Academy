package com.ufovanguard.planetpulseacademy.ui.app

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ufovanguard.planetpulseacademy.data.repository.UserCredentialRepository
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetPulseAcademyViewModel @Inject constructor(
	private val userCredentialRepository: UserCredentialRepository,
	savedStateHandle: SavedStateHandle
): BaseViewModel<PlanetPulseAcademyState>(
	defaultState = PlanetPulseAcademyState(),
	savedStateHandle = savedStateHandle
) {

	init {
	    viewModelScope.launch {
			userCredentialRepository.getUserCredential.collectLatest { userCredential ->
				updateState {
					copy(
						userCredential = userCredential
					)
				}
			}
		}
	}

}