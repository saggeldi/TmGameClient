package com.game.tm.features.game.data.entity.details

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val desc_en: String,
    val desc_ru: String,
    val desc_tm: String,
    val id: Int,
    val image: String,
    val name_en: String,
    val name_ru: String,
    val name_tm: String
)