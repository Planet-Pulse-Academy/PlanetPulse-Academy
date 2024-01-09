package com.ufovanguard.planetpulseacademy.data.model.remote.response

/**
 * Response api umum dengan format
 *
 * ```
 * {
 * 	"status": "success"
 * 	"message": "message"
 * }
 * ```
 */
interface CommonSingleResponse {
	val status: String
	val message: String
}
