package com.ufovanguard.planetpulseacademy.foundation.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class QuizManager @Inject constructor() {

	private var currentQuestionIndex = -1

	private val _currentQuestion = MutableStateFlow<Question?>(null)
	val currentQuestion: StateFlow<Question?> = _currentQuestion

	private val _questions = MutableStateFlow(emptyList<Question>())
	val questions: StateFlow<List<Question>> = _questions

	suspend fun setQuestions(questions: List<Question>) {
		_questions.emit(questions)
	}

	fun hasPrevious(): Boolean = questions.value.getOrNull(currentQuestionIndex - 1) != null

	fun hasNext(): Boolean = questions.value.getOrNull(currentQuestionIndex + 1) != null

	suspend fun previous() {
		if (hasPrevious()) {
			currentQuestionIndex--
			_currentQuestion.emit(questions.value[currentQuestionIndex])
		}
	}

	suspend fun next() {
		if (hasNext()) {
			currentQuestionIndex++
			_currentQuestion.emit(questions.value[currentQuestionIndex])
		}
	}

}

interface Question
