package com.ufovanguard.planetpulseacademy.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ufovanguard.planetpulseacademy.data.repository.UserCredentialRepository
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	private val userCredentialRepository: UserCredentialRepository,
	savedStateHandle: SavedStateHandle
): BaseViewModel<HomeState>(
	defaultState = HomeState(),
	savedStateHandle = savedStateHandle
) {

	init {
	    viewModelScope.launch {
			userCredentialRepository.getUserCredential.collectLatest { credential ->
				updateState {
					copy(
						userCredential = credential
					)
				}
			}
		}
	}

}