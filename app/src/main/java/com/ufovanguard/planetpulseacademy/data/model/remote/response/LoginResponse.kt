package com.ufovanguard.planetpulseacademy.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
	@SerializedName("status")
	val status: String,
	@SerializedName("token")
	val token: String
)
