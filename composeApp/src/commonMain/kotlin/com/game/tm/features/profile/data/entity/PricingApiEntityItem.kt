package com.game.tm.features.profile.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class PricingApiEntityItem(
    val clientType: String,
    val created_at: String,
    val desc_en: String,
    val desc_ru: String,
    val desc_tm: String,
    val id: Int,
    val price: Double,
    val title_en: String,
    val title_ru: String,
    val title_tm: String,
    val updated_at: String
)