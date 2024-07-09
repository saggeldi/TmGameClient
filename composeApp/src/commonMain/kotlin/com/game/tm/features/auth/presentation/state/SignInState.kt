package com.game.tm.features.auth.presentation.state

import com.game.tm.features.auth.data.entity.SignInResponse
import kotlinx.serialization.Serializable

@Serializable
data class SignInState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: SignInResponse? = null
)
