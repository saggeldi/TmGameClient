package com.game.tm.features.game.domain.repository

import com.game.tm.core.Resource
import com.game.tm.features.game.data.entity.GameApiEntity
import com.game.tm.features.game.data.entity.GameRequest
import com.game.tm.features.game.data.entity.details.GameDetailsApiEntity
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun getGames(body: GameRequest, token: String): Flow<Resource<GameApiEntity>>
    suspend fun getGameById(id: String, token: String): Flow<Resource<GameDetailsApiEntity>>
}