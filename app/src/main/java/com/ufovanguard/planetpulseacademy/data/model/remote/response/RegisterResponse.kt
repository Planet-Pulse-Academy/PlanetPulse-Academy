package com.ufovanguard.planetpulseacademy.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
	@SerializedName("status")
	override val status: String,
	@SerializedName("message")
	override val message: String
): CommonSingleResponse
