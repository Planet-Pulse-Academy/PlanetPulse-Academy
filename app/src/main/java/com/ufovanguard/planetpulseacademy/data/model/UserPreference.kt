package com.ufovanguard.planetpulseacademy.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserPreference(
	val isFirstInstall: Boolean = false
): Parcelable
