package com.game.tm.features.category.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Asset(
    val type: String,
    val url: String
)