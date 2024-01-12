package com.ufovanguard.planetpulseacademy.ui.profile

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.ufovanguard.planetpulseacademy.data.model.UserProfile
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class ProfileState(
	val userProfile: UserProfile? = null,
): Parcelable
