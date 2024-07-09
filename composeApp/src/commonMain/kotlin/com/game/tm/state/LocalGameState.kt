package com.game.tm.state

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.game.tm.features.game.data.entity.GameRequest

val LocalGameState = compositionLocalOf {
    mutableStateOf(GameRequest())
}