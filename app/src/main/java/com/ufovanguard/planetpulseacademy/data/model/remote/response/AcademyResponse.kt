package com.ufovanguard.planetpulseacademy.data.model.remote.response

import com.ufovanguard.planetpulseacademy.data.model.Academy

data class AcademyResponse(
	override val status: String,
	override val data: List<Academy>
): CommonMultipleResponse<Academy>
