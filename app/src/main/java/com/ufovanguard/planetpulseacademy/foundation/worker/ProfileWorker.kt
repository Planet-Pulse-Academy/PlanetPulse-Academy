package com.ufovanguard.planetpulseacademy.foundation.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import com.ufovanguard.planetpulseacademy.data.model.UserCredential
import com.ufovanguard.planetpulseacademy.data.model.remote.response.ErrorResponse
import com.ufovanguard.planetpulseacademy.data.repository.UserProfileRepository
import com.ufovanguard.planetpulseacademy.data.repository.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class ProfileWorker @AssistedInject constructor(
	@Assisted private val context: Context,
	@Assisted params: WorkerParameters,
	private val userProfileRepository: UserProfileRepository,
	private val userRepository: UserRepository
): CoroutineWorker(context, params) {
	override suspend fun doWork(): Result {
		return WorkerUtil.tryApiRequest(
			extraErrorMsg = EXTRA_ERROR_MESSAGE,
			onTimeout = {
				Result.failure(
					workDataOf(
						EXTRA_ERROR_MESSAGE to "Timeout"
					)
				)
			}
		) {
			val token = UserCredential.getBearerToken(inputData.getString(EXTRA_TOKEN)!!)
			val response = userRepository.profile(token)

			if (response.isSuccessful) {
				userProfileRepository.setProfile(
					response.body()!!.data[0].also {
						Timber.i("User profile: $it")
					}
				)
				Result.success(
					workDataOf(
						EXTRA_TOKEN to token
					)
				)
			} else {
				val errMsg = response.errorBody().let {
					if (it != null) Gson().fromJson(it.charStream(), ErrorResponse::class.java).message
					else ""
				}

				Result.failure(
					workDataOf(
						LoginWorker.EXTRA_ERROR_MESSAGE to errMsg
					)
				)
			}
		}
	}

	companion object {
		const val EXTRA_ERROR_MESSAGE = "errMsg"

		const val EXTRA_TOKEN = "token"
	}
}