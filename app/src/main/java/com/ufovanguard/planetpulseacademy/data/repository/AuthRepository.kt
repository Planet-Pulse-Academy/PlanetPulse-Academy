package com.ufovanguard.planetpulseacademy.data.repository

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
import retrofit2.Response

interface AuthRepository {

	suspend fun login(body: LoginBody): Response<LoginResponse>

	suspend fun register(body: RegisterBody): Response<RegisterResponse>

	suspend fun forgotPassword(body: ForgotPasswordBody): Response<ForgotPasswordResponse>

	suspend fun verifyOtp(
		email: String,
		body: VerifyOtpBody
	): Response<VerifyOtpResponse>

	suspend fun resetPassword(
		code: String,
		body: ResetPasswordBody
	): Response<ResetPasswordResponse>

}