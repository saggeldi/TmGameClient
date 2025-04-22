package com.game.tm.features.game.data.entity

import kotlinx.serialization.Serializable

class GameSort {
    companion object {
        const val NEWEST_FIRST = "sort_by_date_desc"
        const val OLDEST_FIRST = "sort_by_date_asc"
        const val GAME_SIZE = 20
    }
}

@Serializable
data class GameRequest(
    val page: Int = 1,
    val size: Int = GameSort.GAME_SIZE,
    val sort: String = GameSort.NEWEST_FIRST,
    val categoryId: Int? = null,
    val categoryName: String? = null,
    val location: String = "GLOBAL",
)
