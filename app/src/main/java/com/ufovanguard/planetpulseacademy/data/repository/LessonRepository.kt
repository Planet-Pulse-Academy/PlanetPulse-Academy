package com.ufovanguard.planetpulseacademy.data.repository

import com.ufovanguard.planetpulseacademy.data.model.Lesson
import com.ufovanguard.planetpulseacademy.data.model.remote.response.LessonResponse
import retrofit2.Response

interface LessonRepository {

	suspend fun getAllLesson(
		token: String
	): Response<LessonResponse>

	suspend fun getLesson(
		token: String,
		id: String
	): Response<LessonResponse>

	suspend fun insertLocalLesson(vararg lesson: Lesson)

	suspend fun insertLocalLesson(lessonList: Collection<Lesson>)

	suspend fun updateLocalLesson(vararg lesson: Lesson)

	suspend fun updateLocalLesson(lessonList: Collection<Lesson>)

	suspend fun deleteLocalLesson(vararg lesson: Lesson)

	suspend fun deleteLocalLesson(lessonList: Collection<Lesson>)
}