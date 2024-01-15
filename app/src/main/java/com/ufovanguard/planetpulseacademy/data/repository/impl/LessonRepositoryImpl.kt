package com.ufovanguard.planetpulseacademy.data.repository.impl

import com.ufovanguard.planetpulseacademy.data.datasource.local.dao.LessonDao
import com.ufovanguard.planetpulseacademy.data.datasource.remote.LessonService
import com.ufovanguard.planetpulseacademy.data.model.Lesson
import com.ufovanguard.planetpulseacademy.data.model.remote.response.LessonResponse
import com.ufovanguard.planetpulseacademy.data.repository.LessonRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class LessonRepositoryImpl @Inject constructor(
	private val lessonService: LessonService,
	private val lessonDao: LessonDao
): LessonRepository {

	override suspend fun getAllLesson(token: String): Response<LessonResponse> {
		return lessonService.getAllLesson(token)
	}

	override suspend fun getLesson(token: String, id: String): Response<LessonResponse> {
		return lessonService.getLesson(token, id)
	}

	override fun getAllLocalLesson(): Flow<List<Lesson>> {
		return lessonDao.getAll()
	}

	override fun getLocalLessonById(id: String): Flow<Lesson?> {
		return lessonDao.getById(id)
	}

	override suspend fun insertLocalLesson(vararg lesson: Lesson) {
		lessonDao.insert(*lesson)
	}

	override suspend fun insertLocalLesson(lessonList: Collection<Lesson>) {
		lessonDao.insert(lessonList)
	}

	override suspend fun updateLocalLesson(vararg lesson: Lesson) {
		lessonDao.update(*lesson)
	}

	override suspend fun updateLocalLesson(lessonList: Collection<Lesson>) {
		lessonDao.update(lessonList)
	}

	override suspend fun deleteLocalLesson(vararg lesson: Lesson) {
		lessonDao.delete(*lesson)
	}

	override suspend fun deleteLocalLesson(lessonList: Collection<Lesson>) {
		lessonDao.delete(lessonList)
	}
}