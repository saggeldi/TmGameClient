package com.game.tm.features.server.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ServerApiItem(
    val category: Int,
    val created_at: String,
    val display_host: String,
    val display_port: String,
    val game: List<Int>,
    val id: Int,
    val location: String,
    val server_host: String,
    val server_name: String,
    val server_password: String,
    val server_port: Int,
    val server_username: String,
    val speed: Int,
    val type: String,
    val updated_at: String
)