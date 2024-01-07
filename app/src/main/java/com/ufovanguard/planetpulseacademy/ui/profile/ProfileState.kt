package com.ufovanguard.planetpulseacademy.ui.profile

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class ProfileState(
	val any: String = ""
): Parcelable
