package com.ufovanguard.planetpulseacademy.data.model.remote.body

import com.ufovanguard.planetpulseacademy.foundation.common.RetrofitRequestBody

data class ForgotPasswordBody(
	val email: String
): RetrofitRequestBody() {
	override fun getBody(): Map<String, Any> {
		return mapOf(
			"email" to email
		)
	}
}
