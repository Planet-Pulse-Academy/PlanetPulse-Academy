package com.ufovanguard.planetpulseacademy.foundation.extension

import com.ufovanguard.planetpulseacademy.ProtoUserProfile
import com.ufovanguard.planetpulseacademy.data.model.UserProfile

fun ProtoUserProfile.toUserProfile(): UserProfile = UserProfile(
	id = id,
	username = username,
	email = email,
	name = name,
	isVerified = isVerified,
	defaultColor = defaultColor,
	photoProfile = photoProfile,
	publicId = publicId,
	status = status,
	createdAt = createdAt,
	updatedAt = updatedAt,
	__v = __v
)

fun UserProfile.toProtoUserProfile(): ProtoUserProfile = ProtoUserProfile(
	id = id,
	username = username,
	email = email,
	name = name,
	isVerified = isVerified,
	defaultColor = defaultColor,
	photoProfile = photoProfile,
	publicId = publicId,
	status = status,
	createdAt = createdAt,
	updatedAt = updatedAt,
	__v = __v
)
