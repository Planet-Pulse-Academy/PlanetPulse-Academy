package com.ufovanguard.planetpulseacademy.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserCredential(
	val id: String,
	val name: String,
	val email: String,
): Parcelable {

	val isLoggedIn: Boolean
		get() = id.isNotBlank() and name.isNotBlank() and email.isNotBlank()

}
