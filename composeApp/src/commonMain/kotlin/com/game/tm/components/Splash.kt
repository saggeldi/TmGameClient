package com.game.tm.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.app_name
import tmgame.composeapp.generated.resources.splash

@Composable
fun Splash(onClose: ()-> Unit) {

    LaunchedEffect(true) {
        delay(400L)
        onClose()
    }
    Window(
        onCloseRequest = {
            onClose()
        },
        transparent = true,
        undecorated = true,
        state = rememberWindowState(
            position = WindowPosition(Alignment.Center)
        )
    ) {
        Box(
            Modifier.fillMaxSize().background(
            MaterialTheme.colorScheme.primary,
            RoundedCornerShape(22.dp)
        ).clip(RoundedCornerShape(22.dp)), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(Res.drawable.splash),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                Modifier.fillMaxSize().background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black,
                    )
                )
            ))
            Column(modifier = Modifier.align(Alignment.BottomCenter), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    stringResource(Res.string.app_name),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )
                Spacer(Modifier.height(8.dp))
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}