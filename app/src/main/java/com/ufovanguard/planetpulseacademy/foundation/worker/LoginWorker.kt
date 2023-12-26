package com.ufovanguard.planetpulseacademy.foundation.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.auth0.android.jwt.JWT
import com.google.gson.Gson
import com.ufovanguard.planetpulseacademy.data.datasource.remote.AuthService
import com.ufovanguard.planetpulseacademy.data.model.remote.body.LoginBody
import com.ufovanguard.planetpulseacademy.data.model.remote.response.ErrorResponse
import com.ufovanguard.planetpulseacademy.data.repository.UserCredentialRepository
import com.ufovanguard.planetpulseacademy.foundation.extension.fromJson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class LoginWorker @AssistedInject constructor(
	@Assisted private val context: Context,
	@Assisted params: WorkerParameters,
	private val userCredentialRepository: UserCredentialRepository,
	private val authService: AuthService
): CoroutineWorker(context, params) {

	override suspend fun doWork(): Result {
		return WorkerUtil.tryApiRequest(
			extraErrorMsg = RegisterWorker.EXTRA_ERROR_MESSAGE,
			onTimeout = {
				Result.failure(
					workDataOf(
						RegisterWorker.EXTRA_ERROR_MESSAGE to "Timeout"
					)
				)
			}
		) {
			val loginBody = inputData.getString(EXTRA_REQUEST_BODY)!!.fromJson(LoginBody::class.java)
			val response = authService.login(loginBody.toRequestBody())

			if (response.isSuccessful) {
				val jwt = JWT(response.body()!!.token)
				userCredentialRepository.apply {
					val id = jwt.claims["id"]!!.asString()!!
					val name = jwt.claims["name"]!!.asString()!!
					val email = jwt.claims["email"]!!.asString()!!

					setId(id)
					setName(name)
					setEmail(email)
					setToken(response.body()!!.token)

					Timber.i("Login success: id = $id, name = $name, email = $email")
				}

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