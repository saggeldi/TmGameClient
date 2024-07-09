package com.game.tm.features.category.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val created_at: String,
    val desc_en: String,
    val desc_ru: String,
    val desc_tm: String,
    val id: Int,
    val image: String,
    val name_en: String,
    val name_ru: String,
    val name_tm: String,
    val update_at: String
)