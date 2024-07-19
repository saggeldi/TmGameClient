package com.game.tm.features.server.presentation.state

import com.game.tm.features.server.data.entity.ServerApiItem

data class ServerState(
    val loading: Boolean = true,
    val error: String? = null,
    val data: List<ServerApiItem>? = null
)
