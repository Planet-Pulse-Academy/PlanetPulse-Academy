package com.ufovanguard.planetpulseacademy.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ufovanguard.planetpulseacademy.data.model.Academy
import com.ufovanguard.planetpulseacademy.foundation.base.datasource.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface AcademyDao: BaseDao<Academy> {

	@Query("SELECT * FROM academy")
	fun getAll(): Flow<List<Academy>>

	@Query("SELECT * FROM academy WHERE _id LIKE :id")
	fun getById(id: String): Flow<Academy?>

}