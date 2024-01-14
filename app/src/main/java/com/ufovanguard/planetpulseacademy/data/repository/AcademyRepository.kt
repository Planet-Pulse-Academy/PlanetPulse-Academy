package com.ufovanguard.planetpulseacademy.data.repository

import com.ufovanguard.planetpulseacademy.data.model.Academy
import com.ufovanguard.planetpulseacademy.data.model.remote.response.AcademyResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AcademyRepository {

	suspend fun getRemoteAcademy(token: String): Response<AcademyResponse>

	fun getAllLocalAcademy(): Flow<List<Academy>>

	fun getLocalAcademyById(id: String): Flow<Academy?>

	suspend fun insertLocalAcademy(vararg academy: Academy)

	suspend fun insertLocalAcademy(academyList: Collection<Academy>)

	suspend fun updateLocalAcademy(vararg academy: Academy)

	suspend fun updateLocalAcademy(academyList: Collection<Academy>)

	suspend fun deleteLocalAcademy(vararg academy: Academy)

	suspend fun deleteLocalAcademy(academyList: Collection<Academy>)

}