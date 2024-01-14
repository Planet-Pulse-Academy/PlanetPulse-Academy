package com.ufovanguard.planetpulseacademy.ui.academy

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ufovanguard.planetpulseacademy.data.repository.AcademyRepository
import com.ufovanguard.planetpulseacademy.data.repository.UserCredentialRepository
import com.ufovanguard.planetpulseacademy.foundation.base.ui.BaseViewModel
import com.ufovanguard.planetpulseacademy.foundation.worker.Workers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class AcademyViewModel @Inject constructor(
	private val userCredentialRepository: UserCredentialRepository,
	private val academyRepository: AcademyRepository,
	private val workManager: WorkManager,
	savedStateHandle: SavedStateHandle
): BaseViewModel<AcademyState>(
	defaultState = AcademyState(),
	savedStateHandle = savedStateHandle
) {

	private val _currentGetAcademyWorkId = MutableStateFlow<UUID?>(null)
	private val currentGetAcademyWorkId: StateFlow<UUID?> = _currentGetAcademyWorkId

	init {
		viewModelScope.launch {
			currentGetAcademyWorkId.filterNotNull().flatMapLatest { id ->
				workManager.getWorkInfoByIdFlow(id)
			}.collectLatest { workInfo ->
				updateState {
					when (workInfo.state) {
						WorkInfo.State.FAILED, WorkInfo.State.SUCCEEDED -> {
							copy(isRefreshing = false)
						}
						else -> this
					}
				}
			}
		}

		viewModelScope.launch {
			userCredentialRepository.getUserCredential.collectLatest { cred ->
				updateState {
					copy(
						userCredential = cred
					)
				}
			}
		}

		viewModelScope.launch {
			academyRepository.getAllLocalAcademy().collectLatest { academyList ->
				updateState {
					copy(
						academies = academyList
					)
				}
			}
		}
	}

	fun setSearchQuery(q: String) {
		updateState {
			copy(
				searchQuery = q
			)
		}
	}

	fun refresh() {
		state.value.userCredential?.let { cred ->
			viewModelScope.launch {
				updateState {
					copy(
						isRefreshing = true
					)
				}

				workManager.enqueue(
					Workers.getAcademy(cred.getBearerToken()).also {
						_currentGetAcademyWorkId.emit(it.id)
					}
				)
			}
		}
	}

	fun search() {

	}

}