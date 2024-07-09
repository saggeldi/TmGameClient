package com.game.tm.features.category.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val assets: List<Asset>,
    val id: Int,
    val title_en: String,
    val title_ru: String,
    val title_tm: String
) {
    fun getFirstImage(): String {
        return try {
            assets.sortedBy { it.url }.first { it.type == "image" }.url
        } catch (ex: Exception) {
            ""
        }
    }
}