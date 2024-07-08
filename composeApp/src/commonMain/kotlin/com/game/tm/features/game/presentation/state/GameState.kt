package com.game.tm.features.game.presentation.state

import com.game.tm.features.game.data.entity.GameApiEntity

data class GameState(
    val loading: Boolean = true,
    val error: String? = null,
    val data: GameApiEntity? = null
)
