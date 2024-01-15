package com.ufovanguard.planetpulseacademy.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ufovanguard.planetpulseacademy.data.model.Lesson
import com.ufovanguard.planetpulseacademy.foundation.base.datasource.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao: BaseDao<Lesson> {

	@Query("SELECT * FROM lesson")
	fun getAll(): Flow<List<Lesson>>

	@Query("SELECT * FROM lesson WHERE _id LIKE :id")
	fun getById(id: String): Flow<Lesson?>

}