package com.game.tm.features.server.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterDefaults
import com.dokar.sonner.rememberToasterState
import com.game.tm.components.AppError
import com.game.tm.components.AppLoading
import com.game.tm.features.auth.presentation.ui.PaymentTime
import com.game.tm.features.game.presentation.ui.details.GameServer
import com.game.tm.features.profile.presentation.ui.ProfileScreen
import com.game.tm.features.server.presentation.viewmodel.ServerViewmodel
import com.game.tm.state.LocalStrings
import com.game.tm.state.Routes
import org.jetbrains.compose.resources.painterResource
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.settings
import tmgame.composeapp.generated.resources.team_speak

object TeamSpeakTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(Res.drawable.team_speak)
            val title = LocalStrings.current.teamSpeak
            val id = Routes.TEAM_SPEAK
            return TabOptions(
                id,
                icon = icon,
                title = title
            )
        }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewmodel: ServerViewmodel = navigator.koinNavigatorScreenModel()
        val teamSpeakState = viewmodel.teamSpeak.collectAsState()
        val strings = LocalStrings.current
        val clipboard = LocalClipboardManager.current
        LaunchedEffect(true) {
            viewmodel.initTeamSpeak()
        }
        val toast = rememberToasterState()
        Toaster(
            state = toast,
            alignment = Alignment.TopCenter,
            richColors = true,
            darkTheme = true
        )
        Column(Modifier.fillMaxSize()) {
            PaymentTime(modifier = Modifier.fillMaxWidth()) {
                Row(
                    Modifier.fillMaxWidth()
                        .padding(top = 22.dp, bottom = 22.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = strings.teamSpeak,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    )
                }
            }
            if(teamSpeakState.value.loading) {
                AppLoading(Modifier.fillMaxSize())
            } else if(teamSpeakState.value.error.isNullOrEmpty().not()) {
                AppError(Modifier.fillMaxSize(), message = teamSpeakState.value.error.toString())
            } else {
                teamSpeakState.value.data?.let { list->
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(300.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
                    ) {
                        items(list.size) { index->
                            val item = list[index]
                            Column(Modifier.fillMaxWidth().clip(
                                RoundedCornerShape(12.dp)
                            ).background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp)
                            ).clickable {

                            }.padding(16.dp)) {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.W500
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = item.ipAddress.plus(":").plus(item.port),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(Modifier.height(16.dp))
                                Button(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        clipboard.setText(
                                            buildAnnotatedString {
                                                append("${item.ipAddress}:${item.port}")
                                            }
                                        )
                                        toast.show(
                                            message = strings.copied,
                                            duration = ToasterDefaults.DurationShort,
                                            type = ToastType.Success
                                        )
                                    }
                                ) {
                                    Text(
                                        text = strings.copy,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.W500
                                        ),
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}