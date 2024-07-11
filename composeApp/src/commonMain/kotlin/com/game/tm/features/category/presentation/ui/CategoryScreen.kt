package com.game.tm.features.category.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.game.tm.features.category.presentation.viewmodel.CategoryViewModel
import com.game.tm.state.LocalStrings
import com.game.tm.state.Routes
import org.jetbrains.compose.resources.painterResource
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.category_game

object CategoryTab: Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(Res.drawable.category_game)
            val title = LocalStrings.current.categories
            val index: UShort = Routes.CATEGORY

            return TabOptions(
                index, title, icon
            )
        }

    @Composable
    override fun Content() {
        Navigator(Category())
    }

}

class Category: Screen {
    @Composable
    override fun Content() {
        CategoryScreen()
    }

}

@Composable
fun CategoryScreen() {
    val nav = LocalNavigator.currentOrThrow
    val viewModel = nav.koinNavigatorScreenModel<CategoryViewModel>()
    val state = viewModel.state.collectAsState()
    LaunchedEffect(true) {
        viewModel.initCategories()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth()
                .padding(top = 22.dp, bottom = 22.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = LocalStrings.current.categories,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            )
        }

        if(state.value.loading) {
            AppLoading(Modifier.fillMaxSize())
        } else if(state.value.error.isNullOrEmpty().not()) {
            AppError(message = state.value.error.toString())
        } else {
            state.value.data?.let { list->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    items(list.size) {
                        CategoryItem(modifier = Modifier.fillMaxWidth(), item = list[it])
                    }
                }
            }
        }



    }
}