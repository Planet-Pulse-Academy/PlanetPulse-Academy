package com.ufovanguard.planetpulseacademy.ui.lesson

import androidx.lifecycle.SavedStateHandle
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle
): BaseViewModel<LessonState>(
	defaultState = LessonState(),
	savedStateHandle = savedStateHandle
) {



}