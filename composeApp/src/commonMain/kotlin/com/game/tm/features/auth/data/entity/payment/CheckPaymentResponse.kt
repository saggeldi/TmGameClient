package com.game.tm.features.auth.data.entity.payment

import kotlinx.serialization.Serializable

@Serializable
data class CheckPaymentResponse(
    val days: Int,
    val history: History,
    val user: User
)