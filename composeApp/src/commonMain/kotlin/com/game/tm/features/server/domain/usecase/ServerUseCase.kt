package com.game.tm.features.server.domain.usecase

import com.game.tm.core.Resource
import com.game.tm.features.server.data.entity.ServerApiItem
import com.game.tm.features.server.data.entity.TeamSpeakApiEntityItem
import com.game.tm.features.server.domain.repository.ServerRepository
import kotlinx.coroutines.flow.Flow

class ServerUseCase(
    private val repository: ServerRepository
) {
    suspend fun getTeamSpeak(): Flow<Resource<List<TeamSpeakApiEntityItem>>> {
        return repository.getTeamSpeak()
    }

    suspend fun getServers(location: String): Flow<Resource<List<ServerApiItem>>> {
        return repository.getServers(location)
    }
}