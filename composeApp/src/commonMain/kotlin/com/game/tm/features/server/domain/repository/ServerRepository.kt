package com.game.tm.features.server.domain.repository

import com.game.tm.core.Resource
import com.game.tm.features.server.data.entity.ServerApiItem
import com.game.tm.features.server.data.entity.TeamSpeakApiEntityItem
import kotlinx.coroutines.flow.Flow

interface ServerRepository {
    suspend fun getTeamSpeak(): Flow<Resource<List<TeamSpeakApiEntityItem>>>
    suspend fun getServers(location: String): Flow<Resource<List<ServerApiItem>>>
}