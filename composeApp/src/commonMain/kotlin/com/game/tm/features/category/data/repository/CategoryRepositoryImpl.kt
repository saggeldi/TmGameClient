package com.game.tm.features.category.data.repository

import com.game.tm.core.Constant
import com.game.tm.core.Resource
import com.game.tm.features.category.data.entity.CategoryApiEntityItem
import com.game.tm.features.category.domain.repository.CategoryRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CategoryRepositoryImpl(private val httpClient: HttpClient): CategoryRepository {
    override suspend fun getCategories(): Flow<Resource<List<CategoryApiEntityItem>>> = flow {
        emit(Resource.Loading())
        try {
            val httpResponse = httpClient.get("${Constant.BASE_URL}/category")
            if (httpResponse.status.value in 200..299) {
                emit(Resource.Success(httpResponse.body()))
            } else {
                println("Error: "+httpResponse.status.description)
                emit(Resource.Error(httpResponse.status.description))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage))
        }
    }
}