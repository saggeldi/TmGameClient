package com.game.tm.features.category.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.game.tm.components.RemoteImage
import com.game.tm.core.translateValue
import com.game.tm.features.category.data.entity.CategoryApiEntityItem
import com.game.tm.features.game.presentation.ui.GameTab
import com.game.tm.features.game.presentation.ui.details.GameDetailScreen
import com.game.tm.state.LocalGameState
import org.jetbrains.compose.resources.painterResource
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.placeholder
import tmgame.composeapp.generated.resources.splash

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    item: CategoryApiEntityItem
) {
    val route = LocalNavigator.currentOrThrow
    val navigator = LocalTabNavigator.current
    val context = LocalPlatformContext.current
    val request = LocalGameState.current
    Row (
        modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        RemoteImage(
            url = item.category.image,
            contentDescription = null,
            modifier = Modifier.width(200.dp).height(250.dp).clip(
                RoundedCornerShape(
                    bottomStart = 12.dp,
                    topStart = 12.dp
                )
            ),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(Res.drawable.placeholder),
            error = painterResource(Res.drawable.placeholder)
        )
        Column(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    translateValue(item.category, "name"),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.W600
                    )
                )
                IconButton(
                    onClick = {
                        request.value = request.value.copy(
                            page = 1,
                            categoryId = item.category.id,
                            categoryName = item.category.name_tm
                        )
                        navigator.current = GameTab

                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(Modifier.height(22.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                items(item.games) { game->
                    RemoteImage(
                        url = game.getFirstImage(),
                        contentDescription = null,
                        modifier = Modifier.size(140.dp).clip(RoundedCornerShape(12.dp)).clickable {
                            route.push(GameDetailScreen(game.id.toString()))
                        },
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(Res.drawable.placeholder),
                        error = painterResource(Res.drawable.placeholder)
                    )
                }
            }
        }
    }
}