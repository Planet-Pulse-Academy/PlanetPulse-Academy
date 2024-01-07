package com.ufovanguard.planetpulseacademy.foundation.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import com.ufovanguard.planetpulseacademy.data.model.remote.body.ForgotPasswordBody
import com.ufovanguard.planetpulseacademy.data.model.remote.response.ErrorResponse
import com.ufovanguard.planetpulseacademy.data.repository.AuthRepository
import com.ufovanguard.planetpulseacademy.foundation.extension.fromJson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ForgotPasswordWorker @AssistedInject constructor(
	@Assisted private val context: Context,
	@Assisted params: WorkerParameters,
	private val authRepository: AuthRepository
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
			val response = authRepository.forgotPassword(
				inputData.getString(EXTRA_REQUEST_BODY)!!.fromJson(ForgotPasswordBody::class.java)
			)

			if (response.isSuccessful) {
				Result.success()
			} else {
				val errMsg = response.errorBody().let {
					if (it != null) Gson().fromJson(it.charStream(), ErrorResponse::class.java).message
					else ""
				}

				Result.failure(
					workDataOf(
						EXTRA_ERROR_MESSAGE to errMsg
					)
				)
			}
		}
	}

	companion object {
		const val EXTRA_ERROR_MESSAGE = "errMsg"

		const val EXTRA_REQUEST_BODY = "requestBody"
	}

}