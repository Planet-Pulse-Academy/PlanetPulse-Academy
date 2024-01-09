package com.ufovanguard.planetpulseacademy.data.repository

import com.ufovanguard.planetpulseacademy.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {

	val getUserProfile: Flow<UserProfile>

	suspend fun setProfile(profile: UserProfile)

}