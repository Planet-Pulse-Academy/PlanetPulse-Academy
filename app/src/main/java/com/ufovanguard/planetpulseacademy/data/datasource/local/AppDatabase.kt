package com.ufovanguard.planetpulseacademy.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ufovanguard.planetpulseacademy.data.datasource.local.dao.AcademyDao
import com.ufovanguard.planetpulseacademy.data.datasource.local.dao.LessonDao
import com.ufovanguard.planetpulseacademy.data.model.Academy
import com.ufovanguard.planetpulseacademy.data.model.Lesson

@Database(
	entities = [
		Academy::class,
		Lesson::class,
	],
	version = 1,
	exportSchema = false
)
@TypeConverters(DatabaseTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

	abstract fun academyDao(): AcademyDao
	abstract fun lessonDao(): LessonDao

}