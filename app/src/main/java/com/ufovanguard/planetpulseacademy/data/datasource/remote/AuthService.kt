package com.ufovanguard.planetpulseacademy.data.datasource.remote

import com.ufovanguard.planetpulseacademy.data.model.remote.response.LoginResponse
import com.ufovanguard.planetpulseacademy.data.model.remote.response.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

	@POST("/api/user/register")
	suspend fun register(
		@Body body: RequestBody
	): Response<RegisterResponse>

	@POST("/api/user/login")
	suspend fun login(
		@Body body: RequestBody
	): Response<LoginResponse>

}