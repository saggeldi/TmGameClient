package com.game.tm.features.auth.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    val email: String,
    val fullname: String,
    val password: String,
    val phone: String,
    val stream_id: String,
    val username: String,
    val id: Int,
    val deleted: Boolean,
    val clientType: String,
    val created_at: String,
    val updated_at: String,
    val token: String,
)
