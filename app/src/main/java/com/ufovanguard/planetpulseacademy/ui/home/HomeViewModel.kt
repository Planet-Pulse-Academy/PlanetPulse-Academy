package com.ufovanguard.planetpulseacademy.ui.home

import androidx.lifecycle.SavedStateHandle
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle
): BaseViewModel<HomeState>(
	defaultState = HomeState(),
	savedStateHandle = savedStateHandle
) {

}