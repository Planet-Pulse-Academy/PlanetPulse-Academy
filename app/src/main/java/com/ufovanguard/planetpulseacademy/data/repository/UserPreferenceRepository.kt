package com.ufovanguard.planetpulseacademy.data.repository

import com.ufovanguard.planetpulseacademy.data.model.UserPreference
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {

	val getUserPreference: Flow<UserPreference>

	suspend fun setIsFirstInstall(first: Boolean)

}