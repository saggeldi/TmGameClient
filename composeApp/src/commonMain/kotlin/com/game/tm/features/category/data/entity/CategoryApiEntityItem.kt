package com.game.tm.features.category.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class CategoryApiEntityItem(
    val category: Category,
    val games: List<Game>
)