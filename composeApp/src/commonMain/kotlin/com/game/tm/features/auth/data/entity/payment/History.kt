package com.game.tm.features.auth.data.entity.payment

import kotlinx.serialization.Serializable

@Serializable
data class History(
    val amount: Int,
    val created_at: String,
    val id: Int,
    val updated_at: String,
    val with_key: Boolean
)