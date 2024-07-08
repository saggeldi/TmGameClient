package com.game.tm.features.category.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val assets: List<Asset>,
    val id: Int,
    val title_en: String,
    val title_ru: String,
    val title_tm: String
)