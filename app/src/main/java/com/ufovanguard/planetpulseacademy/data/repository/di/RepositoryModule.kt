package com.ufovanguard.planetpulseacademy.data.repository.di

import androidx.datastore.core.DataStore
import com.ufovanguard.planetpulseacademy.ProtoUserCredential
import com.ufovanguard.planetpulseacademy.ProtoUserPreference
import com.ufovanguard.planetpulseacademy.ProtoUserProfile
import com.ufovanguard.planetpulseacademy.data.datasource.remote.AuthService
import com.ufovanguard.planetpulseacademy.data.datasource.remote.UserService
import com.ufovanguard.planetpulseacademy.data.repository.AuthRepository
import com.ufovanguard.planetpulseacademy.data.repository.UserCredentialRepository
import com.ufovanguard.planetpulseacademy.data.repository.UserPreferenceRepository
import com.ufovanguard.planetpulseacademy.data.repository.UserProfileRepository
import com.ufovanguard.planetpulseacademy.data.repository.UserRepository
import com.ufovanguard.planetpulseacademy.data.repository.impl.AuthRepositoryImpl
import com.ufovanguard.planetpulseacademy.data.repository.impl.UserCredentialRepositoryImpl
import com.ufovanguard.planetpulseacademy.data.repository.impl.UserPreferenceRepositoryImpl
import com.ufovanguard.planetpulseacademy.data.repository.impl.UserProfileRepositoryImpl
import com.ufovanguard.planetpulseacademy.data.repository.impl.UserRepositoryImpl
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

	@Provides
	@Singleton
	fun provideAuthRepository(
		authService: AuthService
	): AuthRepository = AuthRepositoryImpl(authService)

	@Provides
	@Singleton
	fun provideUserProfileRepository(
		userProfileDatastore: DataStore<ProtoUserProfile>
	): UserProfileRepository = UserProfileRepositoryImpl(userProfileDatastore)

	@Provides
	@Singleton
	fun provideUserRepository(
		userService: UserService
	): UserRepository = UserRepositoryImpl(userService)

}