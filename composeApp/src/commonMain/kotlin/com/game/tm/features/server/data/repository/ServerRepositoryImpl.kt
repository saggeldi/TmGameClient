package com.game.tm.features.server.data.repository

import com.game.tm.core.Constant
import com.game.tm.core.Resource
import com.game.tm.features.auth.presentation.viewmodel.AuthSettings
import com.game.tm.features.server.data.entity.ServerApiItem
import com.game.tm.features.server.data.entity.TeamSpeakApiEntityItem
import com.game.tm.features.server.domain.repository.ServerRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ServerRepositoryImpl(
    private val httpClient: HttpClient,
    private val authSettings: AuthSettings
) : ServerRepository {
    override suspend fun getTeamSpeak(): Flow<Resource<List<TeamSpeakApiEntityItem>>> = flow {
        emit(Resource.Loading())
        try {
            val result = httpClient.get("${Constant.BASE_URL}/game-servers") {
                headers.append("Authorization", "Bearer ${authSettings.getUserInfo().token}")
            }
            if (result.status.value in 200..299) {
                emit(Resource.Success(result.body()))
            } else {
                emit(Resource.Error(result.status.description, result.status.value))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override suspend fun getServers(location: String): Flow<Resource<List<ServerApiItem>>> = flow {
        emit(Resource.Loading())
        try {
            val result = httpClient.get("${Constant.BASE_URL}/server/get-client-servers") {
                headers.append("Authorization", "Bearer ${authSettings.getUserInfo().token}")
                url {
                    parameters.append("location", location)
                }
            }
            if (result.status.value in 200..299) {
                emit(Resource.Success(result.body()))
            } else {
                emit(Resource.Error(result.status.description, result.status.value))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}