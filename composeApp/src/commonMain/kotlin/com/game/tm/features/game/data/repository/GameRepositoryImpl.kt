package com.game.tm.features.game.data.repository

import com.game.tm.core.Constant
import com.game.tm.core.Resource
import com.game.tm.features.game.data.entity.GameApiEntity
import com.game.tm.features.game.data.entity.GameRequest
import com.game.tm.features.game.data.entity.details.GameDetailsApiEntity
import com.game.tm.features.game.domain.repository.GameRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.append
import io.ktor.http.contentType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class GameRepositoryImpl(
    private val httpClient: HttpClient
): GameRepository {
    override suspend fun getGames(body: GameRequest, token: String): Flow<Resource<GameApiEntity>> = flow {
        try {
            emit(Resource.Loading())
            delay(1000L)
            val response = httpClient.post("${Constant.BASE_URL}/game/get-games") {
                contentType(ContentType.Application.Json)
                setBody(buildJsonObject {
                    put("page", body.page)
                    put("sort", body.sort)
                    put("size", body.size)
                    put("categoryId", body.categoryId)
                })
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }
            if(response.status.value in 200..299) {
                emit(Resource.Success(response.body()))
            } else {
                emit(Resource.Error(response.status.description, response.status.value))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage))
        }
    }

    override suspend fun getGameById(
        id: String,
        token: String
    ): Flow<Resource<GameDetailsApiEntity>> = flow {
        try {
            emit(Resource.Loading())
            val response = httpClient.get("${Constant.BASE_URL}/game/get-game-by-id/$id") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }
            if(response.status.value in 200..299) {
                emit(Resource.Success(response.body()))
            } else {
                emit(Resource.Error(response.status.description, response.status.value))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage))
        }
    }
}