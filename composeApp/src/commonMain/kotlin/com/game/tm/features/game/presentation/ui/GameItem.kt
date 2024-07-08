package com.game.tm.features.game.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.game.tm.features.game.data.entity.Game
import com.game.tm.state.LocalGameState
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.splash

@Composable
fun GameItem(
    modifier: Modifier = Modifier,
    game: Game,
    onClick: () -> Unit
) {
    val context = LocalPlatformContext.current

    Column(
        modifier.width(200.dp).clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }.background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        AsyncImage(
            model = game.getFirstImage(),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(300.dp).clip(
                RoundedCornerShape(
                    topEnd = 12.dp,
                    topStart = 12.dp
                )
            ),
            imageLoader = ImageLoader(context),
            contentScale = ContentScale.Crop,
            error = painterResource(Res.drawable.splash),
            placeholder = painterResource(Res.drawable.splash),
        )
        Row(modifier = Modifier.padding(12.dp)) {
            Text(
                game.title_tm,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W500
                )
            )
        }
    }
}