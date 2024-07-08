package com.game.tm.features.game.domain.usecase

import com.game.tm.core.Resource
import com.game.tm.features.game.data.entity.GameApiEntity
import com.game.tm.features.game.data.entity.GameRequest
import com.game.tm.features.game.data.entity.details.GameDetailsApiEntity
import com.game.tm.features.game.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

class GameUseCase(
    private val repository: GameRepository
) {
    suspend fun getGames(body: GameRequest, token: String): Flow<Resource<GameApiEntity>> {
        return repository.getGames(body, token)
    }
    suspend fun getGameById(id: String, token: String): Flow<Resource<GameDetailsApiEntity>> {
        return repository.getGameById(id, token)
    }
}