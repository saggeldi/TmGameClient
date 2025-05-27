package com.game.tm.features.auth.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterDefaults
import com.dokar.sonner.rememberToasterState
import com.game.tm.components.AppLogo
import com.game.tm.components.MainScreen
import com.game.tm.features.auth.presentation.viewmodel.AuthViewModel
import com.game.tm.features.profile.presentation.ui.PricingScreen
import com.game.tm.state.LocalStrings

class Payment: Screen {
    @Composable
    override fun Content() {
        PaymentScreen(single = true)
    }

}

@Composable
fun PaymentScreen(modifier: Modifier = Modifier, single: Boolean = false) {
    val nav = LocalNavigator.currentOrThrow
    val strings = LocalStrings.current
    val authViewModel = nav.koinNavigatorScreenModel<AuthViewModel>()
    val key = authViewModel.key
    val toast = rememberToasterState()
    Toaster(toast, richColors = true, darkTheme = true, alignment = Alignment.TopCenter)
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        if(single) {
            Spacer(Modifier.height(22.dp))
            AppLogo()
            Spacer(Modifier.height(22.dp))
        }
        Column(modifier = Modifier.fillMaxWidth(if(single) 0.5f else 1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                strings.pay_with_key,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )
            Spacer(Modifier.height(22.dp))
            AuthInput(
                value = key.value,
                onChange = {
                    authViewModel.changeKey(it)
                },
                icon = Icons.Outlined.Info,
                label = strings.key
            )

            Spacer(Modifier.height(16.dp))

            AuthButton(
                loading = authViewModel.payState.value.loading,
                modifier = Modifier.fillMaxWidth(),
                text = strings.key,
                onClick = {
                    authViewModel.pay(onError = { message->
                        toast.show(
                            message = message,
                            type = ToastType.Error,
                            duration = ToasterDefaults.DurationLong
                        )
                    }) {
                        nav.replace(MainScreen())
                    }
                },
                selected = key.value.length>8
            )

            Spacer(Modifier.height(12.dp))

            TextButton(
                onClick = {
                    nav.push(PricingScreen())
                }
            ) {
                Text(strings.see_plan, color = MaterialTheme.colorScheme.onSurface)
            }

        }
    }
}