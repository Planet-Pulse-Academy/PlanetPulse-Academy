package com.ufovanguard.planetpulseacademy.data.model.remote.response

interface CommonMultipleResponse<E> {
	val status: String
	val data: List<E>
}