package com.ufovanguard.planetpulseacademy.data.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.ufovanguard.planetpulseacademy.ProtoUserCredential
import com.ufovanguard.planetpulseacademy.ProtoUserPreference
import com.ufovanguard.planetpulseacademy.ProtoUserProfile
import com.ufovanguard.planetpulseacademy.data.Constant
import com.ufovanguard.planetpulseacademy.data.datastore.UserCredentialSerializer
import com.ufovanguard.planetpulseacademy.data.datastore.UserPreferenceSerializer
import com.ufovanguard.planetpulseacademy.data.datastore.UserProfileSerializer
import com.ufovanguard.planetpulseacademy.data.repository.impl.UserCredentialRepositoryImpl
import com.ufovanguard.planetpulseacademy.data.repository.impl.UserPreferenceRepositoryImpl
import com.ufovanguard.planetpulseacademy.data.repository.impl.UserProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatastoreModule {

	@Provides
	@Singleton
	fun provideUserCredentialDataStore(
		@ApplicationContext context: Context,
	): DataStore<ProtoUserCredential> = DataStoreFactory.create(
		serializer = UserCredentialSerializer,
		corruptionHandler = UserCredentialRepositoryImpl.corruptionHandler,
		produceFile = { context.dataStoreFile(Constant.DATASTORE_USER_CREDENTIAL) }
	)

	@Provides
	@Singleton
	fun provideUserPreferenceDataStore(
		@ApplicationContext context: Context,
	): DataStore<ProtoUserPreference> = DataStoreFactory.create(
		serializer = UserPreferenceSerializer,
		corruptionHandler = UserPreferenceRepositoryImpl.corruptionHandler,
		produceFile = { context.dataStoreFile(Constant.DATASTORE_USER_PREFERENCE) }
	)

	@Provides
	@Singleton
	fun provideUserProfileDataStore(
		@ApplicationContext context: Context,
	): DataStore<ProtoUserProfile> = DataStoreFactory.create(
		serializer = UserProfileSerializer,
		corruptionHandler = UserProfileRepositoryImpl.corruptionHandler,
		produceFile = { context.dataStoreFile(Constant.DATASTORE_USER_PROFILE) }
	)

}
