package com.game.tm.features.game.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.ToasterDefaults
import com.dokar.sonner.rememberToasterState
import com.game.tm.core.Constant
import com.game.tm.core.translateValue
import com.game.tm.features.auth.presentation.viewmodel.AuthSettings
import com.game.tm.features.game.data.entity.Game
import com.game.tm.state.LocalStrings
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.placeholder
import tmgame.composeapp.generated.resources.splash

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GameItem(
    modifier: Modifier = Modifier,
    game: Game,
    onClick: () -> Unit
) {
    val context = LocalPlatformContext.current
    val strings = LocalStrings.current
    val open = rememberSaveable {
        mutableStateOf(false)
    }
    val authSettings = koinInject<AuthSettings>()
    val clipboard = LocalClipboardManager.current
    val toaster = rememberToasterState()

    Toaster(
        toaster,
        richColors = true,
        alignment = Alignment.TopCenter,
        darkTheme = true
    )

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
            model = "${Constant.BASE_URL}/${game.getFirstImage()}",
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(300.dp).clip(
                RoundedCornerShape(
                    topEnd = 12.dp,
                    topStart = 12.dp
                )
            ),
            imageLoader = ImageLoader(context),
            contentScale = ContentScale.FillBounds,
            error = painterResource(Res.drawable.placeholder),
            placeholder = painterResource(Res.drawable.placeholder),
        )
        Row(modifier = Modifier.padding(12.dp)) {
            Text(
                translateValue(game, "title"),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W500
                )
            )
        }

        val filtered = game.server.filter {  authSettings.checkAccess(it.type) }
        Row(
            modifier = Modifier.fillMaxWidth().clickable {
                open.value = open.value.not()
            }.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                strings.servers,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W500
                )
            )
            Icon(
                if(open.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        if(filtered.isNotEmpty()) {

            AnimatedVisibility(open.value) {
                Column {
                    repeat(filtered.size) {index->
                        val isHovered = rememberSaveable {
                            mutableStateOf(false)
                        }

                        Text(
                            if(isHovered.value) strings.clickToCopy else filtered[index].display_host + ":" + filtered[index].display_port,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth().pointerMoveFilter(
                                onEnter = {
                                    isHovered.value = true
                                    false
                                },
                                onExit = {
                                    isHovered.value = false
                                    false
                                }
                            ).clickable {
                                clipboard.setText(buildAnnotatedString {
                                    append(filtered[index].server_host + ":" + filtered[index].server_port)
                                })
                                toaster.show(
                                    message = strings.copied,
                                    duration = ToasterDefaults.DurationShort,
                                    type = ToastType.Success
                                )
                            }.padding(6.dp)
                        )
                    }
                }
            }
        }


    }
}