package com.ufovanguard.planetpulseacademy.ui.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ufovanguard.planetpulseacademy.data.datasource.local.LocalQuestionDataProvider
import com.ufovanguard.planetpulseacademy.data.model.SelectableQuestionOption
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import com.ufovanguard.planetpulseacademy.foundation.common.QuizManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle
): BaseViewModel<QuizState>(
	savedStateHandle = savedStateHandle,
	defaultState = QuizState()
) {

	private val quizManager = QuizManager<SelectableQuestionOption>()

	init {
		viewModelScope.launch {
			// TODO: Get questions
			quizManager.setQuestions(LocalQuestionDataProvider.values.map { SelectableQuestionOption(it) })
		}

		viewModelScope.launch {
			quizManager.questions.collect { questions ->
				updateState {
					copy(
						selectableQuestionOptions = questions
					)
				}
			}
		}

		viewModelScope.launch {
			quizManager.currentQuestion.collect { question ->
				updateState {
					copy(
						currentQuestion = question,
						currentQuestionIndex = quizManager.currentQuestionIndex
					)
				}
			}
		}
	}

	/**
	 * Change selected option in current question
	 */
	fun changeSelectedQuestionOption(index: Int) {
		viewModelScope.launch {
			quizManager.updateQuestion(state.value.currentQuestionIndex) { copy(selectedOptionIndex = index) }
		}
	}

	/**
	 * Jump to [index]
	 */
	fun changeQuestion(index: Int) {
		viewModelScope.launch {
			quizManager.seekTo(index)
		}
	}

}