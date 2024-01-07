package com.ufovanguard.planetpulseacademy.data.model.remote.body

import com.ufovanguard.planetpulseacademy.foundation.common.RetrofitRequestBody

data class VerifyOtpBody(
	val code: String
): RetrofitRequestBody() {
	override fun getBody(): Map<String, Any> {
		return mapOf(
			"code" to code
		)
	}
}
