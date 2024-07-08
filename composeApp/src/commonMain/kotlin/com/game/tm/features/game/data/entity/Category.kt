package com.game.tm.features.game.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val name_en: String,
    val name_ru: String,
    val name_tm: String
)