package com.game.tm.features.game.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class GameApiEntity(
    val games: List<Game>,
    val total: Int,
    val pages: Int,
)