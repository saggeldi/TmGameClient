package com.game.tm.features.profile.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.game.tm.core.Resource
import com.game.tm.features.profile.domain.usecase.ProfileUseCase
import com.game.tm.features.profile.presentation.state.PricingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfileViewModel(private val useCase: ProfileUseCase): ScreenModel {
    private val _pricingState = MutableStateFlow(PricingState())
    val pricingState = _pricingState.asStateFlow()

    fun getPricing() {
        screenModelScope.launch {
            useCase.getPricing().onEach {
                when(it) {
                    is Resource.Error -> {
                        _pricingState.value = _pricingState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }
                    is Resource.Loading -> {
                        _pricingState.value = _pricingState.value.copy(
                            loading = true,
                            error = it.message,
                            data = it.data
                        )
                    }
                    is Resource.Success -> {
                        _pricingState.value = _pricingState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun initPricing() {
        if(_pricingState.value.data.isNullOrEmpty()) {
            getPricing()
        }
    }
}