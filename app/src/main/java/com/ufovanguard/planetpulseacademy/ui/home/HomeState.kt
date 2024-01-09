package com.ufovanguard.planetpulseacademy.ui.home

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.ufovanguard.planetpulseacademy.data.model.UserCredential
import kotlinx.parcelize.Parcelize

@Parcelize
@Stable
data class HomeState(
	val userCredential: UserCredential? = null
): Parcelable
