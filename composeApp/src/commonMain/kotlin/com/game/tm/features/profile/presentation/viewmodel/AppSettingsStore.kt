package com.game.tm.features.profile.presentation.viewmodel

import com.russhwolf.settings.Settings

class ThemeMode {
    companion object {
        const val LIGHT = "light"
        const val DARK = "dark"
        const val SYSTEM = "system"
    }
}

class AppSettingsStore(private val settings: Settings) {
    fun saveMode(value: String) {
        settings.putString("mode", value)
    }
    fun getMode(): String {
        return settings.getString("mode", ThemeMode.SYSTEM)
    }
}