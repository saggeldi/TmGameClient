package com.game.tm.features.game.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val assets: List<Asset>,
    val category: Category,
    val created_at: String,
    val desc_en: String,
    val desc_ru: String,
    val desc_tm: String,
    val id: Int,
    val site_url: String,
    val star: Int,
    val steam_id: String,
    val title_en: String,
    val title_ru: String,
    val title_tm: String,
    val updated_at: String
) {
    fun getFirstImage(): String {
        return try {
            assets.sortedBy { it.id }.first { it.type == "image" }.url
        } catch (ex: Exception) {
            ""
        }
    }
}