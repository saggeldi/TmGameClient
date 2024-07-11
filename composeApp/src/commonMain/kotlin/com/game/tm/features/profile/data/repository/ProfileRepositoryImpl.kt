package com.game.tm.features.profile.data.repository

import com.game.tm.core.Constant
import com.game.tm.core.Resource
import com.game.tm.features.profile.data.entity.PricingApiEntityItem
import com.game.tm.features.profile.domain.repository.ProfileRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepositoryImpl(private val httpClient: HttpClient): ProfileRepository {
    override suspend fun getPricing(): Flow<Resource<List<PricingApiEntityItem>>> = flow {
        emit(Resource.Loading())
        try {
            val httpResponse = httpClient.get("${Constant.BASE_URL}/pricing")
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