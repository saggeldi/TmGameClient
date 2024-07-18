package com.game.tm.features.auth.presentation.state

import com.game.tm.features.auth.data.entity.SignInResponse
import com.game.tm.features.auth.data.entity.payment.CheckPaymentResponse
import kotlinx.serialization.Serializable

@Serializable
data class CheckPaymentState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: CheckPaymentResponse? = null
)
