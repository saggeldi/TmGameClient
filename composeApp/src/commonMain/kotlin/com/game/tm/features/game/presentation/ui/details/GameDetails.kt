package com.game.tm.features.game.presentation.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.game.tm.components.AppError
import com.game.tm.components.AppLoading
import com.game.tm.features.auth.presentation.viewmodel.AuthSettings
import com.game.tm.features.game.data.entity.details.Server
import com.game.tm.features.game.presentation.viewmodel.GameViewModel
import com.game.tm.openUrl
import com.game.tm.theme.LocalThemeIsDark
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.blocked_server
import tmgame.composeapp.generated.resources.category
import tmgame.composeapp.generated.resources.copied
import tmgame.composeapp.generated.resources.copy
import tmgame.composeapp.generated.resources.description
import tmgame.composeapp.generated.resources.game
import tmgame.composeapp.generated.resources.game_url
import tmgame.composeapp.generated.resources.lorem
import tmgame.composeapp.generated.resources.open_url
import tmgame.composeapp.generated.resources.p0
import tmgame.composeapp.generated.resources.p2
import tmgame.composeapp.generated.resources.p3
import tmgame.composeapp.generated.resources.server
import tmgame.composeapp.generated.resources.servers
import tmgame.composeapp.generated.resources.star
import tmgame.composeapp.generated.resources.you_can_read_more_details

class GameDetailScreen(
    private val id: String
) : Screen {
    @Composable
    override fun Content() {
        val isDark = LocalThemeIsDark.current
        key(isDark.value) {
            GameDetails(id)
        }
    }

}

@Composable
fun GameDetails(id: String) {
    val navigator = LocalNavigator.currentOrThrow
    val clipboard = LocalClipboardManager.current
    val viewModel = navigator.koinNavigatorScreenModel<GameViewModel>()
    val state = viewModel.singleGameState.collectAsState()
    LaunchedEffect(id) {
        viewModel.getGameById(id)
    }

    if(state.value.loading) {
        AppLoading(Modifier.fillMaxSize())
    } else if(state.value.error.isNullOrEmpty().not()) {
        AppError(message = state.value.error.toString())
    } else {
        state.value.data?.let { game->
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(Modifier.height(22.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            navigator.pop()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(Modifier.width(22.dp))
                    Text(
                        game.title_tm,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Row(Modifier.fillMaxSize()) {
                    Column(Modifier.verticalScroll(rememberScrollState()).weight(70f).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Box(modifier = Modifier.fillMaxWidth().height(360.dp)) {
                            Image(
                                painter = painterResource(Res.drawable.p0),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(32.dp))
                            )
                            Box(
                                Modifier.fillMaxSize().clip(RoundedCornerShape(32.dp)).background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            MaterialTheme.colorScheme.primary,
                                        )
                                    )
                                )
                            )
                        }
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(22.dp)
                        ) {
                            items(listOf(
                                Res.drawable.p0, Res.drawable.p2, Res.drawable.p3,
                                Res.drawable.p0, Res.drawable.p2, Res.drawable.p3
                            )) {
                                Image(
                                    painter = painterResource(it),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(140.dp).clip(RoundedCornerShape(12.dp)).clickable {

                                    }
                                )
                            }
                        }

                        GameText(
                            title = stringResource(Res.string.category),
                            content = game.category.name_tm,
                        )

                        GameText(
                            title = stringResource(Res.string.description),
                            content = game.desc_tm,
                        )

                    }

                    Column(Modifier.fillMaxHeight().verticalScroll(rememberScrollState()).weight(30f).padding(16.dp)) {
                        Text(
                            text = stringResource(Res.string.servers),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.W500,
                                fontSize = 18.sp
                            )
                        )
                        Spacer(Modifier.height(8.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            repeat(game.server.count()) { index->
                                GameServer(
                                    server = game.server[index]
                                ) { text->
                                    clipboard.setText(buildAnnotatedString {
                                        append(text)
                                    })
                                }
                            }
                        }
                        Spacer(Modifier.height(22.dp))
                        Column(Modifier.fillMaxWidth()) {
                            Text(
                                text = stringResource(Res.string.star),
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.W500,
                                    fontSize = 18.sp
                                )
                            )
                            Spacer(Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                repeat(game.star) {
                                    Icon(Icons.Default.Star, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                        Spacer(Modifier.height(22.dp))

                        GameText(
                            title = stringResource(Res.string.game_url),
                            content = stringResource(Res.string.you_can_read_more_details),
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                openUrl(game.site_url)
                            }
                        ) {
                            Text(stringResource(Res.string.open_url))
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun GameServer(
    modifier: Modifier = Modifier,
    server: Server,
    onCopy: (String) -> Unit
) {
    val copied = remember {
        mutableStateOf(false)
    }
    val authSettings = koinInject<AuthSettings>()
    val blocked = authSettings.checkAccess(server.type).not()
    Row(modifier.fillMaxWidth().background(
        color = if(blocked.not()) Color.Green.copy(alpha = 0.4f) else Color.Gray,
        shape = RoundedCornerShape(12.dp)
    ).padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(Res.drawable.server),
            contentDescription = null,
            modifier = Modifier.size(35.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = if(blocked) stringResource(Res.string.blocked_server) else "${server.display_host}:${server.display_port}",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp
                )
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "${server.speed}KB/s",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp
                )
            )
        }
        Spacer(Modifier.width(12.dp))
        TextButton(
            onClick = {
                onCopy("${server.server_host}:${server.server_port}")
                copied.value = true
            }
        ) {
            Text(stringResource(if(copied.value) Res.string.copied else Res.string.copy))
        }
    }
}

@Composable
fun GameText(
    title: String,
    content: String
) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.W500,
                fontSize = 18.sp
            )
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = content.trim(),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.W400,
                fontSize = 12.sp
            )
        )
    }
}