package com.game.tm.features.category.domain.usecase

import com.game.tm.core.Resource
import com.game.tm.features.category.data.entity.CategoryApiEntityItem
import com.game.tm.features.category.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryUseCase(private val repository: CategoryRepository) {
    suspend fun getCategories(): Flow<Resource<List<CategoryApiEntityItem>>> {
        return repository.getCategories()
    }
}