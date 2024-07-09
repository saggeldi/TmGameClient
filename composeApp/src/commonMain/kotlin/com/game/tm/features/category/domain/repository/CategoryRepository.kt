package com.game.tm.features.category.domain.repository

import com.game.tm.core.Resource
import com.game.tm.features.category.data.entity.CategoryApiEntityItem
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(): Flow<Resource<List<CategoryApiEntityItem>>>
}