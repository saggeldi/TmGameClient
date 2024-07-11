package com.game.tm.features.auth.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.game.tm.components.MainScreen
import com.game.tm.features.auth.presentation.viewmodel.AuthViewModel
import com.game.tm.features.profile.presentation.ui.PricingScreen
import com.game.tm.state.LocalStrings

class Payment: Screen {
    @Composable
    override fun Content() {
        PaymentScreen()
    }

}

@Composable
fun PaymentScreen(modifier: Modifier = Modifier) {
    val nav = LocalNavigator.currentOrThrow
    val strings = LocalStrings.current
    val authViewModel = nav.koinNavigatorScreenModel<AuthViewModel>()
    val key = authViewModel.key
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
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
                authViewModel.pay {
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