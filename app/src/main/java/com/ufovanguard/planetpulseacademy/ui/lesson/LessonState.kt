package com.ufovanguard.planetpulseacademy.ui.lesson

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class LessonState(
	val any: String = ""
): Parcelable
