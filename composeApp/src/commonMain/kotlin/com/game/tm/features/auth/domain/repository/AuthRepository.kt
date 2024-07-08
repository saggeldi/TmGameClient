package com.game.tm.features.auth.domain.repository

import com.game.tm.core.Resource
import com.game.tm.features.auth.data.entity.PayRequest
import com.game.tm.features.auth.data.entity.SignInRequest
import com.game.tm.features.auth.data.entity.SignInResponse
import com.game.tm.features.auth.data.entity.SignUpRequest
import com.game.tm.features.auth.data.entity.SignUpResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(body: SignUpRequest): Flow<Resource<SignUpResponse>>
    suspend fun signIn(body: SignInRequest): Flow<Resource<SignInResponse>>
    suspend fun payWithKey(body: PayRequest): Flow<Resource<SignInResponse>>
}