package com.ufovanguard.planetpulseacademy.data.datasource.remote

import com.ufovanguard.planetpulseacademy.data.model.remote.response.ForgotPasswordResponse
import com.ufovanguard.planetpulseacademy.data.model.remote.response.LoginResponse
import com.ufovanguard.planetpulseacademy.data.model.remote.response.RegisterResponse
import com.ufovanguard.planetpulseacademy.data.model.remote.response.ResetPasswordResponse
import com.ufovanguard.planetpulseacademy.data.model.remote.response.VerifyOtpResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {

	@POST("/api/user/register")
	suspend fun register(
		@Body body: RequestBody
	): Response<RegisterResponse>

	@POST("/api/user/login")
	suspend fun login(
		@Body body: RequestBody
	): Response<LoginResponse>

	@POST("/api/user/forgot-password")
	suspend fun forgotPassword(
		@Body body: RequestBody
	): Response<ForgotPasswordResponse>

	@POST("/api/user/verify-password/{email}")
	suspend fun verifyOtp(
		@Path("email") email: String,
		@Body body: RequestBody
	): Response<VerifyOtpResponse>

	@POST("/api/user/reset-password")
	suspend fun resetPassword(
		@Query("code") code: String,
		@Body body: RequestBody
	): Response<ResetPasswordResponse>

}