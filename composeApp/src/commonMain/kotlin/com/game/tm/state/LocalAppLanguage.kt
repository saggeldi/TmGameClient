package com.game.tm.state

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.game.tm.core.locale.Locales

val LocalAppLanguage = compositionLocalOf {
    mutableStateOf(Locales.TM)
}