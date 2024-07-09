package com.game.tm.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.game.tm.features.auth.presentation.ui.Auth
import com.game.tm.features.auth.presentation.ui.AuthScreen
import com.game.tm.features.auth.presentation.viewmodel.AuthSettings
import com.game.tm.features.category.presentation.ui.CategoryScreen
import com.game.tm.features.game.presentation.ui.GameScreen
import com.game.tm.features.game.presentation.ui.GameTab
import com.game.tm.features.game.presentation.ui.details.GameDetails
import com.game.tm.state.LocalRouter
import com.game.tm.state.RouterEnum
import org.koin.compose.koinInject

@Composable
fun Router() {
    CurrentTab()
}

@Composable
fun MainRoute() {

    val authSettings = koinInject<AuthSettings>()
    val isEnter = remember {
        mutableStateOf(
            authSettings.getUserInfo().token.isNotEmpty() && authSettings.getUserInfo().clientType.lowercase()
                .contains("none").not() && authSettings.getUserInfo().clientType.isNotEmpty()
        )
    }

    Navigator(if (isEnter.value) MainScreen() else Auth())
}