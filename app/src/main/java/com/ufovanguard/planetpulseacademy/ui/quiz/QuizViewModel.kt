package com.ufovanguard.planetpulseacademy.ui.quiz

import androidx.lifecycle.SavedStateHandle
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle
): BaseViewModel<QuizState>(
	savedStateHandle = savedStateHandle,
	defaultState = QuizState()
) {



}