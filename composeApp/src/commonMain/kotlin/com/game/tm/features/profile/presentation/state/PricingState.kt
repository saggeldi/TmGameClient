package com.game.tm.features.profile.presentation.state

import com.game.tm.features.profile.data.entity.PricingApiEntityItem

data class PricingState(
    val loading: Boolean = true,
    val error: String? = null,
    val data: List<PricingApiEntityItem>? = null
)
