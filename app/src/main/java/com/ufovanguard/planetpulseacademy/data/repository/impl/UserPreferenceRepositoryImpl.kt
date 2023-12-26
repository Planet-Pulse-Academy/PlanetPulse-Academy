package com.ufovanguard.planetpulseacademy.data.repository.impl

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import com.ufovanguard.planetpulseacademy.ProtoUserPreference
import com.ufovanguard.planetpulseacademy.data.model.UserPreference
import com.ufovanguard.planetpulseacademy.data.repository.UserPreferenceRepository
import com.ufovanguard.planetpulseacademy.foundation.extension.toUserPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(
	private val userPreferenceDataStore: DataStore<ProtoUserPreference>
): UserPreferenceRepository {

	override val getUserPreference: Flow<UserPreference>
		get() = userPreferenceDataStore.data.map { it.toUserPreference() }

	override suspend fun setIsFirstInstall(first: Boolean) {
		userPreferenceDataStore.updateData { pref ->
			pref.copy(isFirstInstall = first)
		}
	}

	companion object {
		val corruptionHandler = ReplaceFileCorruptionHandler(
			produceNewData = { ProtoUserPreference(isFirstInstall = true) }
		)
	}

}