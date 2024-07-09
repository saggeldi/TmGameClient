package com.game.tm.features.auth.domain.usecase

import com.game.tm.core.Resource
import com.game.tm.features.auth.data.entity.PayRequest
import com.game.tm.features.auth.data.entity.SignInRequest
import com.game.tm.features.auth.data.entity.SignInResponse
import com.game.tm.features.auth.data.entity.SignUpRequest
import com.game.tm.features.auth.data.entity.SignUpResponse
import com.game.tm.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthUseCase(
    private val repository: AuthRepository
) {
    suspend fun signUp(body: SignUpRequest): Flow<Resource<SignUpResponse>> {
        return repository.signUp(body)
    }
    suspend fun signIn(body: SignInRequest): Flow<Resource<SignInResponse>> {
        return repository.signIn(body)
    }
    suspend fun payWithKey(body: PayRequest): Flow<Resource<SignInResponse>> {
        return repository.payWithKey(body)
    }
}