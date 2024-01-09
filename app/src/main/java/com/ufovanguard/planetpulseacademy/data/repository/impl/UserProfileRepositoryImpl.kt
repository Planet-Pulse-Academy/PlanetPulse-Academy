package com.ufovanguard.planetpulseacademy.data.repository.impl

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import com.ufovanguard.planetpulseacademy.ProtoUserProfile
import com.ufovanguard.planetpulseacademy.data.model.UserProfile
import com.ufovanguard.planetpulseacademy.data.repository.UserProfileRepository
import com.ufovanguard.planetpulseacademy.foundation.extension.toProtoUserProfile
import com.ufovanguard.planetpulseacademy.foundation.extension.toUserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
	private val datastore: DataStore<ProtoUserProfile>
): UserProfileRepository {

	override val getUserProfile: Flow<UserProfile>
		get() = datastore.data.map { it.toUserProfile() }

	override suspend fun setProfile(profile: UserProfile) {
		datastore.updateData { profile.toProtoUserProfile() }
	}

	companion object {
		val corruptionHandler = ReplaceFileCorruptionHandler(
			produceNewData = {
				ProtoUserProfile()
			}
		)
	}
}