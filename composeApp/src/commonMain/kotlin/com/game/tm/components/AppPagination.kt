package com.game.tm.components

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
    val state = rememberLazyListState()

    // Calculate the visible page range
    val visiblePages = calculateVisiblePages(page, pages)

    LazyRow(
        state = state,
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Always show first page
        item {
            PageButton(
                pageNumber = 1,
                currentPage = page,
                onClick = { onChange(1) }
            )
            Spacer(Modifier.width(8.dp))
        }

        // Show ellipsis if needed before current page range
        if (visiblePages.first > 2) {
            item {
                Text("...", modifier = Modifier.padding(horizontal = 8.dp))
            }
        }

        // Show visible pages in range
        items(visiblePages.toList().count()) { index ->
            val pageNumber = visiblePages.toList()[index]
            PageButton(
                pageNumber = pageNumber,
                currentPage = page,
                onClick = { onChange(pageNumber) }
            )
            Spacer(Modifier.width(8.dp))
        }

        // Show ellipsis if needed after current page range
        if (visiblePages.last < pages - 1) {
            item {
                Text("...", modifier = Modifier.padding(horizontal = 8.dp))
            }
        }

        // Always show last page if different from first
        if (pages > 1) {
            item {
                PageButton(
                    pageNumber = pages,
                    currentPage = page,
                    onClick = { onChange(pages) }
                )
            }
        }
    }

    HorizontalScrollbar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 12.dp),
        adapter = rememberScrollbarAdapter(state)
    )
}

@Composable
private fun PageButton(
    pageNumber: Int,
    currentPage: Int,
    onClick: () -> Unit
) {
    val color = if (pageNumber == currentPage) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    val container = if (pageNumber == currentPage) {
        MaterialTheme.colorScheme.primary
    } else {
        Color.Transparent
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = color,
            containerColor = container
        )
    ) {
        Text("$pageNumber", color = color)
    }
}

private fun calculateVisiblePages(currentPage: Int, totalPages: Int): IntRange {
    if (totalPages <= 7) {
        return 2..(totalPages - 1)
    }

    // Show 2 pages before and after current page
    var start = maxOf(2, currentPage - 2)
    var end = minOf(totalPages - 1, currentPage + 2)

    // Adjust if we're at the beginning or end
    if (currentPage <= 4) {
        end = 5
    } else if (currentPage >= totalPages - 3) {
        start = totalPages - 4
    }

    return start..end
}