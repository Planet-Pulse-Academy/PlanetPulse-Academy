package com.ufovanguard.planetpulseacademy.ui.profile

import androidx.lifecycle.SavedStateHandle
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle
): BaseViewModel<ProfileState>(
	defaultState = ProfileState(),
	savedStateHandle = savedStateHandle
) {

}