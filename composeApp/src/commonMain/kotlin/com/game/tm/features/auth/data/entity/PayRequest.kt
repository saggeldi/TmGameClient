package com.game.tm.features.auth.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class PayRequest(
    val key: String = "",
    val token: String = ""
)
