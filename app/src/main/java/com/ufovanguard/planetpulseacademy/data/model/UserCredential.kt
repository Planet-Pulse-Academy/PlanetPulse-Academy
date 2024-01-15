package com.ufovanguard.planetpulseacademy.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserCredential(
	val id: String,
	val name: String,
	val email: String,
	val token: String
): Parcelable {

	val isLoggedIn: Boolean
		get() = id.isNotBlank() and name.isNotBlank() and email.isNotBlank() and token.isNotBlank()

	fun getBearerToken(): String = "bearer $token"

	companion object {

		/**
		 * append "bearer" if [token] not contains "bearer"
		 */
		fun getBearerToken(token: String): String {
			return if (!token.contains("bearer")) "bearer $token" else token
		}

	}

}
