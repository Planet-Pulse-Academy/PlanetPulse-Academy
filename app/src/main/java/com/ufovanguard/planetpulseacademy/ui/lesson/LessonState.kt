package com.ufovanguard.planetpulseacademy.ui.lesson

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.ufovanguard.planetpulseacademy.data.model.Lesson
import com.ufovanguard.planetpulseacademy.data.model.UserCredential
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class LessonState(
	val searchQuery: String = "",
	val isRefreshing: Boolean = false,
	val userCredential: UserCredential? = null,
	val lessons: List<Lesson> = emptyList()
): Parcelable
