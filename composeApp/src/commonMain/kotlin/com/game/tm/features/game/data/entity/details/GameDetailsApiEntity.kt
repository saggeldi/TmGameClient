package com.game.tm.features.game.data.entity.details

import kotlinx.serialization.Serializable

@Serializable
data class GameDetailsApiEntity(
    val assets: List<Asset>,
    val created_at: String,
    val desc_en: String,
    val desc_ru: String,
    val desc_tm: String,
    val id: Int,
    val server: List<Server>,
    val site_url: String,
    val star: Int,
    val steam_id: String,
    val title_en: String,
    val title_ru: String,
    val title_tm: String,
    val updated_at: String
)