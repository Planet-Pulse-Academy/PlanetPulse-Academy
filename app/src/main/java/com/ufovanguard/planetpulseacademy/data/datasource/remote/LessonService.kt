package com.ufovanguard.planetpulseacademy.data.datasource.remote

import com.ufovanguard.planetpulseacademy.data.model.remote.response.LessonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface LessonService {

	@GET("/api/lesson")
	suspend fun getAllLesson(
		@Header("Authorization") token: String
	): Response<LessonResponse>

	@GET("/api/lesson/{id}")
	suspend fun getLesson(
		@Header("Authorization") token: String,
		@Path("id") id: String
	): Response<LessonResponse>

}