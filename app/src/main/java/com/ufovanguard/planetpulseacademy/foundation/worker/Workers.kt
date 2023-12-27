package com.ufovanguard.planetpulseacademy.foundation.worker

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.workDataOf
import com.ufovanguard.planetpulseacademy.data.model.remote.body.LoginBody
import com.ufovanguard.planetpulseacademy.data.model.remote.body.RegisterBody
import com.ufovanguard.planetpulseacademy.foundation.extension.toJson

object Workers {

	fun loginWorker(loginBody: LoginBody): OneTimeWorkRequest {
		return OneTimeWorkRequestBuilder<LoginWorker>()
			.setConstraints(
				Constraints(
					requiredNetworkType = NetworkType.CONNECTED
				)
			)
			.setInputData(
				workDataOf(
					LoginWorker.EXTRA_REQUEST_BODY to loginBody.toJson()
				)
			)
			.build()
	}

	fun registerWorker(registerBody: RegisterBody): OneTimeWorkRequest {
		return OneTimeWorkRequestBuilder<RegisterWorker>()
			.setConstraints(
				Constraints(
					requiredNetworkType = NetworkType.CONNECTED
				)
			)
			.setInputData(
				workDataOf(
					RegisterWorker.EXTRA_REQUEST_BODY to registerBody.toJson()
				)
			)
			.build()
	}

}