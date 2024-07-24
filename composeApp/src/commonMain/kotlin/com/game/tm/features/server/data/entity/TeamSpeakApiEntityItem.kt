package com.game.tm.features.server.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class TeamSpeakApiEntityItem(
    val createdAt: String,
    val id: Int,
    val ipAddress: String,
    val name: String,
    val port: Int,
    val status: String,
    val updatedAt: String
)