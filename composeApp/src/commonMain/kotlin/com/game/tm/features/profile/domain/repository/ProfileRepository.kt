package com.game.tm.features.profile.domain.repository

import com.game.tm.core.Resource
import com.game.tm.features.profile.data.entity.PricingApiEntityItem
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getPricing(): Flow<Resource<List<PricingApiEntityItem>>>
}