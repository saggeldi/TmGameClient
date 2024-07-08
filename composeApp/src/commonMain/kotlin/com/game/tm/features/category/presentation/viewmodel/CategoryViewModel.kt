package com.game.tm.features.category.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.game.tm.core.Resource
import com.game.tm.features.category.domain.usecase.CategoryUseCase
import com.game.tm.features.category.presentation.state.CategoryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CategoryViewModel(private val useCase: CategoryUseCase): ScreenModel {
    private val _state = MutableStateFlow(CategoryState())
    val state = _state.asStateFlow()

    fun getCategories() {
        screenModelScope.launch {
            useCase.getCategories().onEach {
                when(it) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            loading = true,
                            error = it.message,
                            data = it.data
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun initCategories() {
        if(_state.value.data.isNullOrEmpty()) {
            getCategories()
        }
    }
}