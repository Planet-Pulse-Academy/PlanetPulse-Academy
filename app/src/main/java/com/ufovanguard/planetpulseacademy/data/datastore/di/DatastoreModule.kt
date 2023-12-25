package com.ufovanguard.planetpulseacademy.data.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.ufovanguard.planetpulseacademy.ProtoUserCredential
import com.ufovanguard.planetpulseacademy.data.Constant
import com.ufovanguard.planetpulseacademy.data.datastore.UserCredentialSerializer
import com.ufovanguard.planetpulseacademy.data.repository.impl.UserCredentialRepositoryImpl
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

}
