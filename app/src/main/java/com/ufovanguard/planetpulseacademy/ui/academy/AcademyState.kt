package com.ufovanguard.planetpulseacademy.ui.academy

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.ufovanguard.planetpulseacademy.data.model.Academy
import com.ufovanguard.planetpulseacademy.data.model.UserCredential
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class AcademyState(
	val searchQuery: String = "",
	val isRefreshing: Boolean = false,
	val userCredential: UserCredential? = null,
	val academies: List<Academy> = emptyList()
): Parcelable
