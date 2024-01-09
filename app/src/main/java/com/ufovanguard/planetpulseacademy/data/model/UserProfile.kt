package com.ufovanguard.planetpulseacademy.data.model

import com.google.gson.annotations.SerializedName

data class UserProfile(
	@SerializedName("_id")
	val id: String,
	@SerializedName("username")
	val username: String,
	@SerializedName("email")
	val email: String,
	@SerializedName("name")
	val name: String,
	@SerializedName("isVerified")
	val isVerified: Boolean,
	@SerializedName("default_color")
	val defaultColor: String,
	@SerializedName("photo_profile")
	val photoProfile: String?,
	@SerializedName("public_id")
	val publicId: String?,
	@SerializedName("status")
	val status: String,
	@SerializedName("createdAt")
	val createdAt: String,
	@SerializedName("updatedAt")
	val updatedAt: String,
	@SerializedName("__v")
	val __v: Int,
//	@SerializedName("academy")
	//	val academy: List<>
)
