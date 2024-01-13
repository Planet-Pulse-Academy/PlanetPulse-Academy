package com.ufovanguard.planetpulseacademy.foundation.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuizManager<T> {

	private var _currentQuestionIndex = -1
	val currentQuestionIndex: Int
		get() = _currentQuestionIndex

	private val _currentQuestion = MutableStateFlow<T?>(null)
	val currentQuestion: StateFlow<T?> = _currentQuestion

	private val _questions = MutableStateFlow(emptyList<T>())
	val questions: StateFlow<List<T>> = _questions

	suspend fun setQuestions(questions: List<T>) {
		_questions.emit(questions)
		_currentQuestionIndex = 0
		_currentQuestion.emit(questions[_currentQuestionIndex])
	}

	fun hasPrevious(): Boolean = questions.value.getOrNull(_currentQuestionIndex - 1) != null

	fun hasNext(): Boolean = questions.value.getOrNull(_currentQuestionIndex + 1) != null

	fun ensureInBounds(index: Int): Boolean {
		return index > 0 || index < questions.value.lastIndex
	}

	/**
	 * @param index question index in [questions]
	 */
	suspend fun updateQuestion(index: Int, newQuestion: T.() -> T) {
		currentQuestion.value?.let {
			_questions.emit(
				questions.value.toMutableList().apply {
					set(index, newQuestion(it))
				}
			)

			_currentQuestion.emit(questions.value[index])
		}
	}

	suspend fun previous() {
		if (hasPrevious()) {
			_currentQuestionIndex--
			_currentQuestion.emit(questions.value[_currentQuestionIndex])
		}
	}

	suspend fun next() {
		if (hasNext()) {
			_currentQuestionIndex++
			_currentQuestion.emit(questions.value[_currentQuestionIndex])
		}
	}

	suspend fun seekTo(index: Int) {
		if (ensureInBounds(index)) {
			_currentQuestionIndex = index
			_currentQuestion.emit(questions.value[_currentQuestionIndex])
		}
	}

}
