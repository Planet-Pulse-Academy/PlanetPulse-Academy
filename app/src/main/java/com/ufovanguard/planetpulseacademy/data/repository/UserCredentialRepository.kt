package com.ufovanguard.planetpulseacademy.data.repository

import com.ufovanguard.planetpulseacademy.data.model.UserCredential
import kotlinx.coroutines.flow.Flow

interface UserCredentialRepository {

	val getUserCredential: Flow<UserCredential>

	suspend fun setId(id: String)

	suspend fun setName(name: String)

	suspend fun setEmail(email: String)

}