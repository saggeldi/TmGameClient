package com.game.tm.features.game.presentation.state

import com.game.tm.features.game.data.entity.details.GameDetailsApiEntity

data class SingleGameState(
    val loading: Boolean = true,
    val error: String? = null,
    val data: GameDetailsApiEntity? = null
)
