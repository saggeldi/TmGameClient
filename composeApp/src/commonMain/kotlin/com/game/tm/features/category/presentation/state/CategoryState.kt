package com.game.tm.features.category.presentation.state

import com.game.tm.features.category.data.entity.CategoryApiEntityItem

data class CategoryState(
    val loading: Boolean = true,
    val error: String? = null,
    val data: List<CategoryApiEntityItem>? = null
)
