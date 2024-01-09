package com.ufovanguard.planetpulseacademy.data.model.remote.response

import com.google.gson.annotations.SerializedName
import com.ufovanguard.planetpulseacademy.data.model.UserProfile

data class ProfileResponse(
	@SerializedName("status")
	override val status: String,
	@SerializedName("data")
	override val data: List<UserProfile>
): CommonMultipleResponse<UserProfile>
