package com.ufovanguard.planetpulseacademy.data.repository.impl

import com.ufovanguard.planetpulseacademy.data.datasource.remote.UserService
import com.ufovanguard.planetpulseacademy.data.model.remote.response.ProfileResponse
import com.ufovanguard.planetpulseacademy.data.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
	private val userService: UserService
): UserRepository {

	override suspend fun profile(token: String): Response<ProfileResponse> {
		return userService.profile(token)
	}
}