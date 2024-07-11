package com.game.tm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.lyricist.ProvideStrings
import cafe.adriel.lyricist.rememberStrings
import com.game.tm.components.MainRoute
import com.game.tm.core.SettingConfig
import com.game.tm.core.locale.EnStrings
import com.game.tm.core.locale.Locales
import com.game.tm.core.locale.RuStrings
import com.game.tm.core.locale.Strings
import com.game.tm.core.locale.TmStrings
import com.game.tm.features.auth.di.initKoin
import com.game.tm.features.game.data.entity.GameRequest
import com.game.tm.features.profile.presentation.viewmodel.AppSettingsStore
import com.game.tm.state.LocalAppLanguage
import com.game.tm.state.LocalGameState
import com.game.tm.state.LocalStrings
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

data class Test(
    val name_tm: String = "Salam",
    val name_ru: String = "Privet",
)

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun App(
    settings: List<SettingConfig<*>>,
    onClearSettings: () -> Unit,
) {

    initKoin()


    val lyricist = rememberStrings<Strings>(
        translations = mapOf(
            Locales.EN to EnStrings,
            Locales.TM to TmStrings,
            Locales.RU to RuStrings,
        )
    )

    KoinContext {
        val appSettingsStore = koinInject<AppSettingsStore>()
        CompositionLocalProvider(
            LocalAppLanguage provides remember {
                mutableStateOf(appSettingsStore.getLanguage())
            },
            LocalGameState provides remember {
                mutableStateOf(GameRequest())
            },
        ) {
            val language = LocalAppLanguage.current

            ProvideStrings(lyricist, LocalStrings) {
                LaunchedEffect(language.value) {
                    appSettingsStore.saveLanguage(language.value)
                    lyricist.languageTag = language.value
                    println(language.value)
                }
                MainRoute()
            }

        }
    }


}

expect fun openUrl(url: String?)