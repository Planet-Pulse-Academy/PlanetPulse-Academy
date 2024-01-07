package com.ufovanguard.planetpulseacademy.foundation.common

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class Timer {

	private val scope = CoroutineScope(Dispatchers.Default + Job())
	private var currentJob: Job? = null
	private var startTime = System.currentTimeMillis()

	private val _isRunning = MutableStateFlow(false)
	val isRunning: StateFlow<Boolean> = _isRunning

	private val _remainingTimeInMilliseconds = MutableStateFlow(0L)
	val remainingTimeInMilliseconds: StateFlow<Long> = _remainingTimeInMilliseconds

	val hasRunningJob: Boolean
		get() = currentJob != null

	fun postDelayed(
		timeInMillis: Long,
		context: CoroutineContext = Dispatchers.Default,
		block: suspend () -> Unit
	) {
		startTime = System.currentTimeMillis() + timeInMillis
		currentJob = scope.launch(context) {
			_isRunning.emit(true)

			var currentTime = System.currentTimeMillis()
			while (startTime > currentTime) {
				currentTime = System.currentTimeMillis()

				_remainingTimeInMilliseconds.emit(
					startTime.milliseconds
						.minus(currentTime.milliseconds)
						.inWholeMilliseconds
						.coerceAtLeast(0L)
				)

				delay(1000)
			}

			block()
			_isRunning.emit(false)
		}
	}

	fun postDelayed(
		duration: Duration,
		context: CoroutineContext = Dispatchers.Default,
		block: suspend () -> Unit
	) = postDelayed(duration.inWholeMilliseconds, context, block)

	fun cancel() {
		currentJob?.cancel(CancellationException("User cancelled"))
	}

}