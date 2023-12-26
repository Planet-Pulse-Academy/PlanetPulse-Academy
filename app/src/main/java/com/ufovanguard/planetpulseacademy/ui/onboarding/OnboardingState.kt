package com.ufovanguard.planetpulseacademy.ui.onboarding

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize

@Parcelize
@Stable
data class OnboardingState(
	val page: Int = 0
): Parcelable
