package com.game.tm.features.game.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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
import com.game.tm.features.game.presentation.ui.details.GameDetailScreen
import com.game.tm.features.game.presentation.ui.details.GameDetails
import com.game.tm.features.game.presentation.viewmodel.GameViewModel
import com.game.tm.state.LocalGameState
import com.game.tm.state.LocalRouter
import com.game.tm.state.RouterEnum
import com.game.tm.state.Routes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.game
import tmgame.composeapp.generated.resources.games
import tmgame.composeapp.generated.resources.home_game
import tmgame.composeapp.generated.resources.p0
import tmgame.composeapp.generated.resources.pb

object GameTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(Res.drawable.games)
            val title = stringResource(Res.string.games)
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
    val router = LocalNavigator.currentOrThrow
    val viewModel = router.koinNavigatorScreenModel<GameViewModel>()
    val state = viewModel.gameState.collectAsState()
    val request = LocalGameState.current
    LaunchedEffect(request.value) {
        viewModel.getGames(request.value)
    }
    Column(modifier = Modifier.fillMaxSize()) {
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
                    text = stringResource(Res.string.games).plus(" / ${request.value.categoryName}"),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                )
            } else {
                Text(
                    text = stringResource(Res.string.games),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                )
            }

        }
        if (state.value.loading) {
            AppLoading(Modifier.fillMaxSize())
        } else if (state.value.error.isNullOrEmpty().not()) {
            AppError(message = state.value.error.toString())
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