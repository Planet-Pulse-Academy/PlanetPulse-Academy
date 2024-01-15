package com.ufovanguard.planetpulseacademy.data.datasource.remote.di

import com.ufovanguard.planetpulseacademy.BuildConfig
import com.ufovanguard.planetpulseacademy.data.datasource.remote.AcademyService
import com.ufovanguard.planetpulseacademy.data.datasource.remote.AuthService
import com.ufovanguard.planetpulseacademy.data.datasource.remote.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

	@Singleton
	@Provides
	fun provideOkHttpClient(): OkHttpClient {
		val client = if (BuildConfig.DEBUG) {
			val loggingInterceptor = HttpLoggingInterceptor().apply {
				setLevel(HttpLoggingInterceptor.Level.BODY)
			}

			OkHttpClient.Builder()
				.addInterceptor(loggingInterceptor)
		} else {
			OkHttpClient
				.Builder()
		}

		return client
			.addInterceptor(
				Interceptor { chain ->
					chain.proceed(
						chain.request().newBuilder()
							.addHeader("Content-Type", "application/json; charset=utf-8")
							.build()
					)
				}
			)
			.connectTimeout(1, TimeUnit.MINUTES)
			.readTimeout(30, TimeUnit.SECONDS)
			.writeTimeout(15, TimeUnit.SECONDS)
			.build()
	}

	@Singleton
	@Provides
	fun provideRetrofit(
		okHttpClient: OkHttpClient
	): Retrofit {
		return Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(BuildConfig.API_BASE_URL)
			.client(okHttpClient)
			.build()
	}

	@Singleton
	@Provides
	fun provideAuthService(
		retrofit: Retrofit
	): AuthService = retrofit.create(AuthService::class.java)

	@Singleton
	@Provides
	fun provideUserService(
		retrofit: Retrofit
	): UserService = retrofit.create(UserService::class.java)

	@Singleton
	@Provides
	fun provideAcademyService(
		retrofit: Retrofit
	): AcademyService = retrofit.create(AcademyService::class.java)

}