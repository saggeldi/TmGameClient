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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.game.tm.components.GlassBackground
import com.game.tm.components.MainRoute
import com.game.tm.components.PlayerDemo
import com.game.tm.components.Router
import com.game.tm.components.Sidebar
import com.game.tm.core.SettingConfig
import com.game.tm.features.auth.di.initKoin
import com.game.tm.features.game.data.entity.GameRequest
import com.game.tm.features.game.presentation.ui.GameTab
import com.game.tm.state.LocalAppLanguage
import com.game.tm.state.LocalGameState
import com.game.tm.state.LocalRouter
import com.game.tm.state.RouterEnum
import com.game.tm.theme.AppTheme
import org.jetbrains.compose.resources.ResourceEnvironment
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinContext
import java.util.Locale

@Composable
internal fun App(
    settings: List<SettingConfig<*>>,
    onClearSettings: () -> Unit,
) {
    var settingConfig by remember { mutableStateOf(settings.first()) }
    var enableLoggingChecked by remember { mutableStateOf(settingConfig.isLoggingEnabled) }

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
           LaunchedEffect(language.value) {
               Locale.setDefault(Locale.forLanguageTag(language.value))
           }
           key(language.value) {
               MainRoute()
           }
       }
   }



}

expect fun openUrl(url: String?)