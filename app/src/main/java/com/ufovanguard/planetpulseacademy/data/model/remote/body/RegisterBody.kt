package com.ufovanguard.planetpulseacademy.data.model.remote.body


import com.google.gson.annotations.SerializedName
import com.ufovanguard.planetpulseacademy.foundation.common.RetrofitRequestBody

data class RegisterBody(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
): RetrofitRequestBody() {

	override fun getBody(): Map<String, Any> {
		return mapOf(
			"email" to email,
			"name" to name,
			"password" to password,
			"username" to username,
		)
	}
}