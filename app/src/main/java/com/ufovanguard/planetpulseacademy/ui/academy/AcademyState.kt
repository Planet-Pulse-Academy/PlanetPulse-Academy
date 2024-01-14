package com.ufovanguard.planetpulseacademy.ui.academy

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class AcademyState(
	val searchQuery: String = ""
): Parcelable
