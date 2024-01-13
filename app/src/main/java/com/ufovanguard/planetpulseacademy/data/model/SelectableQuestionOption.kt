package com.ufovanguard.planetpulseacademy.data.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class SelectableQuestionOption(
	val question: Question,
	/**
	 * `-1` if not selected
	 */
	val selectedOptionIndex: Int = -1
): Parcelable
