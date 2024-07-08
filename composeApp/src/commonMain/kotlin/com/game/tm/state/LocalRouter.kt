package com.game.tm.state

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

class Routes {
    companion object{
        const val GAME: UShort = 1001u
        const val CATEGORY: UShort = 1002u
        const val SETTING: UShort = 1003u
        const val PROFILE: UShort = 1004u
        const val GAME_DETAILS: UShort = 1005u
    }
}

enum class RouterEnum(id: UShort) {
    GAME(Routes.GAME),
    CATEGORY(Routes.CATEGORY),
    SETTING(Routes.SETTING),
    PROFILE(Routes.PROFILE),
    GAME_DETAILS(Routes.GAME_DETAILS),
}

val LocalRouter = compositionLocalOf {
    mutableStateOf<RouterEnum>(RouterEnum.GAME)
}