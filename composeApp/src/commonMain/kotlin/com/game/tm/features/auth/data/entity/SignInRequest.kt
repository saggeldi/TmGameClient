package com.game.tm.features.auth.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val username: String = "",
    val password: String = ""
)
