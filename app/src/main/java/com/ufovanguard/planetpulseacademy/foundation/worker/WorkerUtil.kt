package com.ufovanguard.planetpulseacademy.foundation.worker

import androidx.work.ListenableWorker
import androidx.work.workDataOf
import timber.log.Timber
import java.net.SocketTimeoutException

object WorkerUtil {

	inline fun tryApiRequest(
		extraErrorMsg: String,
		onTimeout: (e: SocketTimeoutException) -> ListenableWorker.Result = { ListenableWorker.Result.retry() },
		onFailure: (e: Exception) -> ListenableWorker.Result = { e ->
			ListenableWorker.Result.failure(
				workDataOf(
					extraErrorMsg to (e.message ?: "")
				)
			)
		},
		block: () -> ListenableWorker.Result
	): ListenableWorker.Result {
		return try {
			block()
		} catch (e: SocketTimeoutException) {
			Timber.e(e, e.message)
			onTimeout(e)
		} catch (e: Exception) {
			Timber.e(e, e.message)
			ListenableWorker.Result.failure(
				workDataOf(
					extraErrorMsg to (e.message ?: "")
				)
			)
		}
	}

}