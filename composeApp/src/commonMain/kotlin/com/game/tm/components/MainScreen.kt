package com.game.tm.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.game.tm.features.auth.presentation.ui.PaymentScreen
import com.game.tm.features.auth.presentation.viewmodel.AuthViewModel
import com.game.tm.features.game.presentation.ui.GameTab
import com.game.tm.state.LocalPaymentState
import com.game.tm.theme.AppTheme

class MainScreen: Screen {

//    override val key: ScreenKey
//        get() = UUID.randomUUID().toString()

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val authViewModel: AuthViewModel = navigator.koinNavigatorScreenModel()
        val checkPaymentState = authViewModel.checkPaymentState

        LaunchedEffect(true) {
            authViewModel.checkPayment()
        }

        if(checkPaymentState.value.loading) {
            AppLoading(Modifier.fillMaxSize())
        } else if (checkPaymentState.value.error.isNullOrEmpty().not()) {
            AppTheme {
                GlassBackground {
                    Box(Modifier.padding(16.dp)) {
                        PaymentScreen(single = true)
                    }
                }
            }
        } else if(checkPaymentState.value.data == null) {
            AppTheme {
                GlassBackground {
                    Box(Modifier.padding(16.dp)) {
                        PaymentScreen(single = true)
                    }
                }
            }
        } else if(checkPaymentState.value.data != null) {
            checkPaymentState.value.data?.let {
                CompositionLocalProvider(
                    LocalPaymentState provides remember {
                        mutableStateOf(it)
                    }
                ) {
                    TabNavigator(GameTab) {
                        AppTheme {
                            GlassBackground {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    Sidebar(Modifier, navigator, checkPaymentState.value.data!!)
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
        }


    }
}