package com.ufovanguard.planetpulseacademy.foundation.base.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(vararg t: T)

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(ts: Collection<T>)

	@Update
	suspend fun update(vararg t: T)

	@Update
	suspend fun update(ts: Collection<T>)

	@Delete
	suspend fun delete(vararg t: T)

	@Delete
	suspend fun delete(ts: Collection<T>)

}