package com.game.tm.features.profile.domain.usecase

import com.game.tm.core.Resource
import com.game.tm.features.profile.data.entity.PricingApiEntityItem
import com.game.tm.features.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend fun getPricing(): Flow<Resource<List<PricingApiEntityItem>>> {
        return repository.getPricing()
    }
}