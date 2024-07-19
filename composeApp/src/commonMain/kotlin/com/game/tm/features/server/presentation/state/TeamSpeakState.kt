package com.game.tm.features.server.presentation.state

import com.game.tm.features.server.data.entity.TeamSpeakApiEntityItem
import kotlinx.serialization.Serializable

@Serializable
data class TeamSpeakState(
    val loading: Boolean = true,
    val error: String? = null,
    val data: List<TeamSpeakApiEntityItem>? = null
)
