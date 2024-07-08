package com.game.tm.state

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

val LocalAppLanguage = compositionLocalOf {
    mutableStateOf("tm")
}