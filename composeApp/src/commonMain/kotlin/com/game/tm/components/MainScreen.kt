package com.game.tm.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.game.tm.features.game.presentation.ui.GameTab
import com.game.tm.theme.AppTheme

class MainScreen: Screen {

//    override val key: ScreenKey
//        get() = UUID.randomUUID().toString()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        TabNavigator(GameTab) {
           AppTheme {
               GlassBackground {
                   Row(
                       modifier = Modifier
                           .fillMaxSize()
                   ) {
                       Sidebar(Modifier, navigator)
                       Box(
                           modifier = Modifier.weight(1f).fillMaxHeight()
                       ) {
                           Router()
                       }
                   }
               }
           }
        }
    }
}