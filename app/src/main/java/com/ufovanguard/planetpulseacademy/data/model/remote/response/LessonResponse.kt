package com.ufovanguard.planetpulseacademy.data.model.remote.response

import com.ufovanguard.planetpulseacademy.data.model.Lesson

data class LessonResponse(
	override val status: String,
	override val data: List<Lesson>
): CommonMultipleResponse<Lesson>
