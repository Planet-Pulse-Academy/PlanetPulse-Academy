package com.ufovanguard.planetpulseacademy.data.datasource.remote

import com.ufovanguard.planetpulseacademy.data.model.remote.response.ProfileResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {

	@GET("/api/user")
	suspend fun profile(
		@Header("Authorization") token: String
	): Response<ProfileResponse>

}