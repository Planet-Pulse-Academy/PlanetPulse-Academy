package com.ufovanguard.planetpulseacademy.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
	@SerializedName("status")
	val status: String,
	@SerializedName("message")
	val message: String
)
