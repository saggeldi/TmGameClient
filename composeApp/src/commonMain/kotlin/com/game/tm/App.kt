package com.game.tm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLocalization
import androidx.compose.ui.platform.LocalPlatformTextInputMethodOverride
import androidx.compose.ui.platform.PlatformLocalization
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.TabNavigator
import coil3.compose.LocalPlatformContext
import com.game.tm.components.GlassBackground
import com.game.tm.components.MainRoute
import com.game.tm.components.PlayerDemo
import com.game.tm.components.Router
import com.game.tm.components.Sidebar
import com.game.tm.core.SettingConfig
import com.game.tm.core.translateValue
import com.game.tm.features.auth.di.initKoin
import com.game.tm.features.game.data.entity.GameRequest
import com.game.tm.features.game.presentation.ui.GameTab
import com.game.tm.state.LocalAppLanguage
import com.game.tm.state.LocalGameState
import com.game.tm.state.LocalRouter
import com.game.tm.state.RouterEnum
import com.game.tm.theme.AppTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.ResourceEnvironment
import org.jetbrains.compose.resources.getSystemResourceEnvironment
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinContext
import java.util.Locale
import java.util.spi.LocaleServiceProvider

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

    KoinContext {
        CompositionLocalProvider(
            LocalAppLanguage provides remember {
                mutableStateOf("tm")
            },
            LocalGameState provides remember {
                mutableStateOf(GameRequest())
            }
        ) {
            val language = LocalAppLanguage.current
            val rtl = remember {
                mutableStateOf(LayoutDirection.Ltr)
            }
            LaunchedEffect(language.value) {
                Locale.setDefault(Locale.forLanguageTag(language.value))
            }
            CompositionLocalProvider(
                LocalLayoutDirection provides rtl.value
            ) {
                MainRoute()
            }

        }
    }


}

expect fun openUrl(url: String?)