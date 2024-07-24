package com.game.tm.features.game.presentation.ui

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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.game.tm.components.AppError
import com.game.tm.components.AppLoading
import com.game.tm.components.AppPagination
import com.game.tm.features.auth.presentation.ui.PaymentTime
import com.game.tm.features.game.presentation.ui.details.GameDetailScreen
import com.game.tm.features.game.presentation.viewmodel.GameViewModel
import com.game.tm.state.LocalGameState
import com.game.tm.state.LocalStrings
import com.game.tm.state.Routes
import org.jetbrains.compose.resources.painterResource
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.games

object GameTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(Res.drawable.games)
            val title = LocalStrings.current.games
            val index: UShort = Routes.GAME

            return TabOptions(
                index, title, icon
            )
        }

    @Composable
    override fun Content() {
        Navigator(Games())
    }

}

class Games : Screen {
    @Composable
    override fun Content() {
        GameScreen()
    }

}

@Composable
fun GameScreen() {
    val strings = LocalStrings.current
    val router = LocalNavigator.currentOrThrow
    val viewModel = router.koinNavigatorScreenModel<GameViewModel>()
    val state = viewModel.gameState.collectAsState()
    val request = LocalGameState.current

    LaunchedEffect(request.value) {
        viewModel.getGames(request.value)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        PaymentTime(modifier = Modifier.fillMaxWidth()) {
            Row(
                Modifier.fillMaxWidth()
                    .padding(top = 22.dp, bottom = 22.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if(request.value.categoryId!=null) {
                    IconButton(
                        onClick = {
                            request.value = request.value.copy(
                                categoryName = null,
                                categoryId = null
                            )
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = strings.games.plus(" / ${request.value.categoryName}"),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    )
                } else {
                    Text(
                        text = strings.games,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    )
                }

            }
        }

        TabRow(
            modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(12.dp)),
            selectedTabIndex = if(request.value.location == "LOCAL") 0 else 1
        )  {
            Tab(
                selected = request.value.location == "LOCAL",
                onClick = {
                    request.value = request.value.copy(
                        location = "LOCAL"
                    )
                },
                text = {
                    androidx.compose.material3.Text(strings.local)
                }
            )
            Tab(
                selected = request.value.location == "GLOBAL",
                onClick = {
                    request.value = request.value.copy(
                        location = "GLOBAL"
                    )
                },
                text = {
                    androidx.compose.material3.Text(strings.global)
                }
            )
        }
        Spacer(Modifier.height(16.dp))

        if (state.value.loading) {
            AppLoading(Modifier.fillMaxSize())
        } else if (state.value.error.isNullOrEmpty().not()) {
            AppError(message = state.value.error.toString(), modifier = Modifier.fillMaxSize())
        } else {
            state.value.data?.let { data ->
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(200.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    items(data.games) { item ->
                        GameItem(
                            game = item
                        ) {
                            router.push(GameDetailScreen(item.id.toString()))
                        }
                    }
                    item(span = {
                        GridItemSpan(maxLineSpan)
                    }) {
                        AppPagination(
                            pages = data.pages,
                            page = request.value.page,
                            onChange = { newPage->
                                request.value = request.value.copy(
                                    page = newPage
                                )
                            }
                        )
                    }
                }
            }

        }
    }
}