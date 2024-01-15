package com.ufovanguard.planetpulseacademy.foundation.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import com.ufovanguard.planetpulseacademy.data.model.UserCredential
import com.ufovanguard.planetpulseacademy.data.model.remote.response.ErrorResponse
import com.ufovanguard.planetpulseacademy.data.repository.LessonRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class GetAllLessonWorker @AssistedInject constructor(
	@Assisted private val context: Context,
	@Assisted params: WorkerParameters,
	private val lessonRepository: LessonRepository
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
			val response = lessonRepository.getAllLesson(token)

			if (response.isSuccessful) {
				lessonRepository.insertLocalLesson(response.body()!!.data)
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
		const val EXTRA_TOKEN = "token"
	}

}