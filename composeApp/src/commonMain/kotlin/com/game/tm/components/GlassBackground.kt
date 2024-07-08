package com.game.tm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

@Composable
fun GlassBackground(
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).background(
        brush = Brush.radialGradient(
            listOf(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                MaterialTheme.colorScheme.background,
            ),
            radius = 1000f,
            center = Offset(500f,100f)
        )
    )) {
        Box(modifier = Modifier.fillMaxSize().background(
            brush = Brush.radialGradient(
                listOf(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    MaterialTheme.colorScheme.background.copy(alpha = 0.1f),
                ),
                radius = 1000f,
                center = Offset.Infinite
            )
        )) {
            content()
        }
    }
}