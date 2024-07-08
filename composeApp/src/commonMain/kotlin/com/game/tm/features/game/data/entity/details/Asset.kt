package com.game.tm.features.game.data.entity.details

import kotlinx.serialization.Serializable

@Serializable
data class Asset(
    val created_at: String,
    val id: Int,
    val type: String,
    val updated_at: String,
    val url: String
)