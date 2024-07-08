package com.game.tm.features.auth.presentation.state

import com.game.tm.features.auth.data.entity.SignUpResponse

data class SignUpState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: SignUpResponse? = null
)
