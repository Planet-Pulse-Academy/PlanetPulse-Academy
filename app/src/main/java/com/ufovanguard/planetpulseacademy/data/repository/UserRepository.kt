package com.ufovanguard.planetpulseacademy.data.repository

import com.ufovanguard.planetpulseacademy.data.model.remote.response.ProfileResponse
import retrofit2.Response

interface UserRepository {

	suspend fun profile(token: String): Response<ProfileResponse>

}