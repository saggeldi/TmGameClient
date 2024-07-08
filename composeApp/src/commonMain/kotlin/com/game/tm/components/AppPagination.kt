package com.game.tm.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppPagination(
    pages: Int = 0,
    page: Int = 1,
    onChange: (Int) -> Unit = {}
) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        repeat(pages) { index ->
            val color =
                if (index + 1 == page) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            val container =
                if (index + 1 == page) MaterialTheme.colorScheme.primary else Color.Transparent
            Button(
                onClick = {
                    onChange(index + 1)
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = color,
                    containerColor = container
                )
            ) {
                Text("${index.plus(1)}", color = color)
            }
            Spacer(Modifier.width(8.dp))
        }
    }
}