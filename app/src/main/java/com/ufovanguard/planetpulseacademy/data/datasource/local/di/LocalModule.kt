package com.ufovanguard.planetpulseacademy.data.datasource.local.di

import android.content.Context
import androidx.room.Room
import com.ufovanguard.planetpulseacademy.data.Constant
import com.ufovanguard.planetpulseacademy.data.datasource.local.AppDatabase
import com.ufovanguard.planetpulseacademy.data.datasource.local.dao.AcademyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

	@Provides
	@Singleton
	fun provideAppDatabase(
		@ApplicationContext context: Context
	): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, Constant.DATABASE_APP)
		.build()

	@Provides
	@Singleton
	fun provideAcademyDao(
		appDatabase: AppDatabase
	): AcademyDao = appDatabase.academyDao()
}