package com.ufovanguard.planetpulseacademy.ui.home

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize

@Parcelize
@Stable
data class HomeState(
	val any: String = ""
): Parcelable
