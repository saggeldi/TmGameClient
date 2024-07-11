package com.game.tm.features.auth.data.repository

import com.game.tm.core.Constant
import com.game.tm.core.Resource
import com.game.tm.features.auth.data.entity.PayRequest
import com.game.tm.features.auth.data.entity.SignInRequest
import com.game.tm.features.auth.data.entity.SignInResponse
import com.game.tm.features.auth.data.entity.SignUpRequest
import com.game.tm.features.auth.data.entity.SignUpResponse
import com.game.tm.features.auth.domain.repository.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(private val httpClient: HttpClient): AuthRepository {
    override suspend fun signUp(body: SignUpRequest): Flow<Resource<SignUpResponse>> = flow {
        emit(Resource.Loading())
        try {
            val httpResponse = httpClient.post("${Constant.BASE_URL}/client/sign-up") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
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

    override suspend fun signIn(body: SignInRequest): Flow<Resource<SignInResponse>> = flow {
        emit(Resource.Loading())
        try {
            val httpResponse = httpClient.post("${Constant.BASE_URL}/client/sign-in") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
            if (httpResponse.status.value in 200..299) {
                emit(Resource.Success(httpResponse.body()))
            } else {
                println("Error: "+httpResponse.status.value)
                emit(Resource.Error(httpResponse.status.description, code = httpResponse.status.value))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage))
        }
    }

    override suspend fun payWithKey(body: PayRequest): Flow<Resource<SignInResponse>> = flow {
        emit(Resource.Loading())
        try {
            val httpResponse = httpClient.post("${Constant.BASE_URL}/client/pay-with-key/"+body.key) {
                contentType(ContentType.Application.Json)
                setBody(body)
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${body.token}")
                }
            }
            if (httpResponse.status.value in 200..299) {
                emit(Resource.Success(httpResponse.body()))
            } else {
                println("Error: "+httpResponse.status.value)
                emit(Resource.Error(httpResponse.status.description, code = httpResponse.status.value))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.localizedMessage))
        }
    }
}