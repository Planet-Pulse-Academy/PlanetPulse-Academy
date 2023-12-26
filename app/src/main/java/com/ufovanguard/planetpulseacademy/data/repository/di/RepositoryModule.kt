package com.ufovanguard.planetpulseacademy.data.repository.di

import androidx.datastore.core.DataStore
import com.ufovanguard.planetpulseacademy.ProtoUserCredential
import com.ufovanguard.planetpulseacademy.ProtoUserPreference
import com.ufovanguard.planetpulseacademy.data.repository.UserCredentialRepository
import com.ufovanguard.planetpulseacademy.data.repository.UserPreferenceRepository
import com.ufovanguard.planetpulseacademy.data.repository.impl.UserCredentialRepositoryImpl
import com.ufovanguard.planetpulseacademy.data.repository.impl.UserPreferenceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

	@Provides
	@Singleton
	fun provideUserCredentialRepository(
		userCredentialDatastore: DataStore<ProtoUserCredential>
	): UserCredentialRepository = UserCredentialRepositoryImpl(userCredentialDatastore)

	@Provides
	@Singleton
	fun provideUserPreferenceRepository(
		userPreferenceDatastore: DataStore<ProtoUserPreference>
	): UserPreferenceRepository = UserPreferenceRepositoryImpl(userPreferenceDatastore)

}