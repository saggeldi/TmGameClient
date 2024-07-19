package com.game.tm.features.profile.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.game.tm.components.AppError
import com.game.tm.components.AppLoading
import com.game.tm.components.GlassBackground
import com.game.tm.features.auth.presentation.ui.PaymentScreen
import com.game.tm.features.auth.presentation.viewmodel.AuthSettings
import com.game.tm.features.profile.presentation.viewmodel.ProfileViewModel
import com.game.tm.state.LocalStrings
import com.game.tm.theme.AppTheme
import org.koin.compose.koinInject

class PricingScreen : Screen {
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        Navigator(Pricing(
            onBack = {
                nav.pop()
            }
        ))
    }
}

class Pricing(private val onBack: () -> Unit): Screen {
    @Composable
    override fun Content() {
        PricingUi(onBack = onBack)
    }

}

@Composable
fun PricingUi(onBack: () -> Unit) {
    val strings = LocalStrings.current
    val nav = LocalNavigator.currentOrThrow
    val authSettings = koinInject<AuthSettings>()
    val viewModel = nav.koinNavigatorScreenModel<ProfileViewModel>()
    val state = viewModel.pricingState.collectAsState()
    LaunchedEffect(true) {
        viewModel.initPricing()
    }
    val i = remember {
        mutableStateOf(0)
    }
    AppTheme {
        GlassBackground {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                IconButton(
                    onClick = {
                        if(i.value==0) {
                            onBack()
                        } else {
                            i.value = 0
                        }
                    }
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
                }
                Text(
                    strings.pricing_title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    strings.pricing_desc,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                if (state.value.loading) {
                    AppLoading(Modifier.fillMaxWidth())
                } else if (state.value.error.isNullOrEmpty().not()) {
                    AppError(message = state.value.error.toString(), modifier = Modifier.fillMaxSize())
                } else {
                    state.value.data?.let { list ->
                        Spacer(Modifier.height(22.dp))
                        if(i.value == 0) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                repeat(list.size) { index ->
                                    val item = list[index]
                                    PricingItem(
                                        modifier = Modifier.weight(1f),
                                        price = item.price.toString().plus(" TMT"),
                                        title = item.title_tm,
                                        description = item.desc_tm,
                                        value = item.clientType,
                                        selected = authSettings.getUserInfo().clientType == item.clientType
                                    ) {
                                        i.value = 1
                                    }
                                }
                            }
                        } else {
                            PaymentScreen()
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun PricingItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    price: String,
    title: String,
    description: String,
    value: String,
    onClick: (String) -> Unit
) {
    val color = if(selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface
    val shape = RoundedCornerShape(16.dp)
    val brush = if(selected) Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary,
        ),
    ) else Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surface,
        ),
    )
    Column(
        modifier = modifier.clip(shape).background(
            shape = shape,
            brush = brush
        ).clickable {
            onClick(value)
        }.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(22.dp))
        Text(
            title, color = color,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        )
        Text(
            price, color = color,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
        )

        Spacer(Modifier.height(12.dp))

        Text(
            description, color = color.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            ),
            lineHeight = 40.sp
        )
        Spacer(Modifier.height(12.dp))
        OutlinedButton(
            onClick = {
                onClick(value)
            }
        ) {
            Text(LocalStrings.current.use_plan, color = color)
        }
        Spacer(Modifier.height(22.dp))
    }
}