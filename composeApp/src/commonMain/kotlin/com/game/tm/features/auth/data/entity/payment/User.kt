package com.game.tm.features.auth.data.entity.payment

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val clientType: String,
    val created_at: String,
    val deleted: Boolean,
    val email: String,
    val fullname: String,
    val id: Int,
    val password: String,
    val phone: String,
    val stream_id: String,
    val updated_at: String,
    val username: String
)