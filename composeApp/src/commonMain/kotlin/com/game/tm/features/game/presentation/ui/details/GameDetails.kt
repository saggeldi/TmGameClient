package com.game.tm.features.game.presentation.ui.details

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.game.tm.components.AppError
import com.game.tm.components.AppLoading
import com.game.tm.core.Constant
import com.game.tm.core.translateValue
import com.game.tm.features.auth.presentation.viewmodel.AuthSettings
import com.game.tm.features.game.data.entity.details.Server
import com.game.tm.features.game.presentation.viewmodel.GameViewModel
import com.game.tm.openUrl
import com.game.tm.state.LocalStrings
import com.game.tm.theme.LocalThemeIsDark
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.server

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
fun GameVideo(
    url: String
) {
    val state = rememberWebViewState("https://google.com")

    WebView(state)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameDetails(id: String) {
    val strings = LocalStrings.current
    val navigator = LocalNavigator.currentOrThrow
    val clipboard = LocalClipboardManager.current
    val viewModel = navigator.koinNavigatorScreenModel<GameViewModel>()
    val state = viewModel.singleGameState.collectAsState()
    val context = LocalPlatformContext.current
    val coroutine = rememberCoroutineScope()
    LaunchedEffect(id) {
        viewModel.getGameById(id)
    }

    if(state.value.loading) {
        AppLoading(Modifier.fillMaxSize())
    } else if(state.value.error.isNullOrEmpty().not()) {
        AppError(message = state.value.error.toString())
    } else {
        state.value.data?.let { game->
            val images = game.assets.filter { it.type == "image" }
            val pagerState = rememberPagerState { images.count() }
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
                        translateValue(game, "title"),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Row(Modifier.fillMaxSize()) {
                    Column(Modifier.verticalScroll(rememberScrollState()).weight(70f).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        HorizontalPager(pagerState, pageSpacing = 12.dp, beyondBoundsPageCount = 2) { index->
                            Box(modifier = Modifier.fillMaxWidth().height(360.dp)) {
                                AsyncImage(
                                    model = "${Constant.BASE_URL}/${images[index].url}",
                                    imageLoader = ImageLoader(context),
                                    contentDescription = null,
                                    contentScale = ContentScale.Inside,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(32.dp)),
                                )
                                Box(
                                    Modifier.fillMaxSize().clip(RoundedCornerShape(32.dp)).background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                                            )
                                        )
                                    )
                                )

                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                            repeat(images.count()) {
                                Box(Modifier.size(15.dp).clip(CircleShape).background(
                                    color = if(it == pagerState.currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                ).clickable {
                                    coroutine.launch {
                                        pagerState.scrollToPage(it)
                                    }
                                })
                                Spacer(Modifier.width(6.dp))
                            }
                        }
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(22.dp)
                        ) {
                            items(images.count()) {
                                AsyncImage(
                                    model = "${Constant.BASE_URL}/${images[it].url}",
                                    imageLoader = ImageLoader(context),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(140.dp).clip(RoundedCornerShape(12.dp)).clickable {
                                        coroutine.launch {
                                            pagerState.scrollToPage(it)
                                        }
                                    }
                                )
                            }
                        }

                        GameText(
                            title = strings.category,
                            content = translateValue(game.category, "name"),
                        )

                        GameText(
                            title = strings.description,
                            content = translateValue(game, "desc"),
                        )

                    }

                    Column(Modifier.fillMaxHeight().verticalScroll(rememberScrollState()).weight(30f).padding(16.dp)) {
                        Text(
                            text = strings.servers,
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
                                text = strings.star,
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
                            title = strings.open_url,
                            content = strings.you_can_read_more_details,
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                openUrl(game.site_url)
                            }
                        ) {
                            Text(strings.open_url)
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
    val strings = LocalStrings.current
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
                text = if(blocked) strings.blocked_server else "${server.display_host}:${server.display_port}",
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
            },
            enabled = blocked.not()
        ) {
            Text(if(copied.value) strings.copied else strings.copy)
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