package com.ufovanguard.planetpulseacademy.ui.quiz

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.ufovanguard.planetpulseacademy.data.model.SelectableQuestionOption
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class QuizState(
	val selectableQuestionOptions: List<SelectableQuestionOption> = emptyList(),
	val currentQuestion: SelectableQuestionOption? = null,
	/**
	 * index of [currentQuestion] in [selectableQuestionOptions]
	 */
	val currentQuestionIndex: Int = -1
): Parcelable
