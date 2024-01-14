package com.ufovanguard.planetpulseacademy.data.model.remote.body

import com.ufovanguard.planetpulseacademy.foundation.common.RetrofitRequestBody

data class AddAcademyBody(
	val idLesson: String
): RetrofitRequestBody() {
	override fun getBody(): Map<String, Any> {
		return mapOf(
			"id_lesson" to idLesson
		)
	}
}
