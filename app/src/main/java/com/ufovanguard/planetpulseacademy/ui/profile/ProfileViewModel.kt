package com.ufovanguard.planetpulseacademy.ui.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ufovanguard.planetpulseacademy.data.repository.UserProfileRepository
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val userProfileRepository: UserProfileRepository,
	savedStateHandle: SavedStateHandle
): BaseViewModel<ProfileState>(
	defaultState = ProfileState(),
	savedStateHandle = savedStateHandle
) {

	init {
	    viewModelScope.launch {
			userProfileRepository.getUserProfile.collectLatest { profile ->
				updateState {
					copy(
						userProfile = profile
					)
				}
			}
		}
	}

}