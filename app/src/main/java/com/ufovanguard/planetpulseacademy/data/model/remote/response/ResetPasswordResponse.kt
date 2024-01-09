package com.ufovanguard.planetpulseacademy.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class ResetPasswordResponse(
	@SerializedName("status")
	override val status: String,
	@SerializedName("message")
	override val message: String
): CommonSingleResponse
