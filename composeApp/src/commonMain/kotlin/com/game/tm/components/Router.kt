package com.game.tm.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import com.game.tm.features.auth.presentation.ui.Auth
import com.game.tm.features.auth.presentation.viewmodel.AuthSettings
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