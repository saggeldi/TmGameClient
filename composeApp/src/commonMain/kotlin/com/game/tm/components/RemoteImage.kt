package com.game.tm.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image

@Composable
fun RemoteImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    placeholder: Painter? = null,
    error: Painter? = null,
    onLoading: (() -> Unit)? = null,
    onSuccess: (() -> Unit)? = null,
    onError: ((Throwable) -> Unit)? = null
) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }

    LaunchedEffect(url) {
        try {
            isLoading = true
            onLoading?.invoke()

            val bitmap = withContext(Dispatchers.IO) {
                loadImageBitmapFromUrl(url)
            }

            imageBitmap = bitmap
            isLoading = false
            onSuccess?.invoke()
        } catch (e: Exception) {
            isLoading = false
            hasError = true
            onError?.invoke(e)
        }
    }

    Box(modifier = modifier) {
        when {
            isLoading && placeholder != null -> {
                Image(
                    painter = placeholder,
                    contentDescription = contentDescription,
                    contentScale = contentScale,
                    modifier = Modifier.matchParentSize()
                )
            }
            hasError && error != null -> {
                Image(
                    painter = error,
                    contentDescription = contentDescription,
                    contentScale = contentScale,
                    modifier = Modifier.matchParentSize()
                )
            }
            imageBitmap != null -> {
                Image(
                    bitmap = imageBitmap!!,
                    contentDescription = contentDescription,
                    contentScale = contentScale,
                    modifier = Modifier.matchParentSize()
                )
            }
        }
    }
}

private suspend fun loadImageBitmapFromUrl(url: String): ImageBitmap {
    val client = HttpClient(CIO)
    try {
        val response: HttpResponse = client.get(url)
        val bytes = response.readBytes()

        // Use Skia to decode the image
        val skiaImage = Image.makeFromEncoded(bytes)
        val bitmap = Bitmap()
        bitmap.allocPixels(skiaImage.imageInfo)
        skiaImage.readPixels(bitmap)

        return bitmap.asImageBitmap()
    } finally {
        client.close()
    }
}