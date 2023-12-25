package com.ufovanguard.planetpulseacademy.data.repository.impl

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import com.ufovanguard.planetpulseacademy.ProtoUserCredential
import com.ufovanguard.planetpulseacademy.data.model.UserCredential
import com.ufovanguard.planetpulseacademy.data.repository.UserCredentialRepository
import com.ufovanguard.planetpulseacademy.foundation.extension.toUserCredential
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserCredentialRepositoryImpl @Inject constructor(
	private val userCredentialDatastore: DataStore<ProtoUserCredential>
): UserCredentialRepository {

	override val getUserCredential: Flow<UserCredential>
		get() = userCredentialDatastore.data.map { it.toUserCredential() }

	override suspend fun setId(id: String) {
		userCredentialDatastore.updateData { protoUserCredential ->
			protoUserCredential.copy(id = id)
		}
	}

	override suspend fun setName(name: String) {
		userCredentialDatastore.updateData { protoUserCredential ->
			protoUserCredential.copy(name = name)
		}
	}

	override suspend fun setEmail(email: String) {
		userCredentialDatastore.updateData { protoUserCredential ->
			protoUserCredential.copy(email = email)
		}
	}

	companion object {
		val corruptionHandler = ReplaceFileCorruptionHandler(
			produceNewData = { ProtoUserCredential() }
		)
	}

}