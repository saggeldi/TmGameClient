package com.game.tm.features.auth.presentation.ui

import BankCardScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

class Payment : Screen {
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
    val selectedIndex = rememberSaveable {
        mutableStateOf(0)
    }
    Toaster(toast, richColors = true, darkTheme = true, alignment = Alignment.TopCenter)
    val pagerState = rememberPagerState { 2 }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (single) {
            Spacer(Modifier.height(22.dp))
            AppLogo()
            Spacer(Modifier.height(22.dp))
        }

        TabRow(
            modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(12.dp)),
            selectedTabIndex = selectedIndex.value
        ) {
            Tab(
                selected = selectedIndex.value == 0,
                onClick = {
                    selectedIndex.value = 0
                },
                text = {
                    androidx.compose.material3.Text("Kod bilen töleg")
                }
            )
            Tab(
                selected = selectedIndex.value == 1,
                onClick = {
                    selectedIndex.value = 1
                },
                text = {
                    androidx.compose.material3.Text("Kard bilen töleg")
                }
            )

        }
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState
        ) { index ->
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (selectedIndex.value == 0) {
                    Column(
                        modifier = Modifier.fillMaxWidth(if (single) 0.5f else 1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            strings.pay_with_key,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                        Spacer(Modifier.height(18.dp))
                        Text(
                            "Töleg açar sözüni almak üçin şu belgä jaň ediň +99361123456",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
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
                                authViewModel.pay(onError = { message ->
                                    toast.show(
                                        message = message,
                                        type = ToastType.Error,
                                        duration = ToasterDefaults.DurationLong
                                    )
                                }) {
                                    nav.replace(MainScreen())
                                }
                            },
                            selected = key.value.length > 8
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
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(if (single) 0.5f else 1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BankCardScreen(
                            loading = authViewModel.payState.value.loading,
                            onPayClick = {
                                authViewModel.changeKey("my_key_1234")
                                authViewModel.pay(onError = { message ->
                                    toast.show(
                                        message = message,
                                        type = ToastType.Error,
                                        duration = ToasterDefaults.DurationLong
                                    )
                                }) {
                                    nav.replace(MainScreen())
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}