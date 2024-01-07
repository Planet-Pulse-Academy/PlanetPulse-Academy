package com.ufovanguard.planetpulseacademy.data.repository.impl

import com.ufovanguard.planetpulseacademy.data.datasource.remote.AuthService
import com.ufovanguard.planetpulseacademy.data.model.remote.body.ForgotPasswordBody
import com.ufovanguard.planetpulseacademy.data.model.remote.body.LoginBody
import com.ufovanguard.planetpulseacademy.data.model.remote.body.RegisterBody
import com.ufovanguard.planetpulseacademy.data.model.remote.body.ResetPasswordBody
import com.ufovanguard.planetpulseacademy.data.model.remote.body.VerifyOtpBody
import com.ufovanguard.planetpulseacademy.data.model.remote.response.ForgotPasswordResponse
import com.ufovanguard.planetpulseacademy.data.model.remote.response.LoginResponse
import com.ufovanguard.planetpulseacademy.data.model.remote.response.RegisterResponse
import com.ufovanguard.planetpulseacademy.data.model.remote.response.ResetPasswordResponse
import com.ufovanguard.planetpulseacademy.data.model.remote.response.VerifyOtpResponse
import com.ufovanguard.planetpulseacademy.data.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
	private val authService: AuthService
): AuthRepository {

	override suspend fun login(body: LoginBody): Response<LoginResponse> {
		return authService.login(body.toRequestBody())
	}

	override suspend fun register(body: RegisterBody): Response<RegisterResponse> {
		return authService.register(body.toRequestBody())
	}

	override suspend fun forgotPassword(body: ForgotPasswordBody): Response<ForgotPasswordResponse> {
		return authService.forgotPassword(body.toRequestBody())
	}

	override suspend fun verifyOtp(
		email: String,
		body: VerifyOtpBody
	): Response<VerifyOtpResponse> {
		return authService.verifyOtp(email, body.toRequestBody())
	}

	override suspend fun resetPassword(
		code: String,
		body: ResetPasswordBody
	): Response<ResetPasswordResponse> {
		return authService.resetPassword(code, body.toRequestBody())
	}
}