package com.game.tm.features.game.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.game.tm.core.Resource
import com.game.tm.features.auth.presentation.viewmodel.AuthSettings
import com.game.tm.features.game.data.entity.GameRequest
import com.game.tm.features.game.domain.usecase.GameUseCase
import com.game.tm.features.game.presentation.state.GameState
import com.game.tm.features.game.presentation.state.SingleGameState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class GameViewModel(
    private val useCase: GameUseCase,
    private val authSettings: AuthSettings
): ScreenModel {
    private val _gameState = MutableStateFlow(GameState())
    val gameState = _gameState.asStateFlow()

    private val _singleGameState = MutableStateFlow(SingleGameState())
    val singleGameState = _singleGameState.asStateFlow()

    private var job: Job? = null

    fun getGames(body: GameRequest) {
        job?.cancel()
        job = screenModelScope.launch {
            useCase.getGames(body, authSettings.getUserInfo().token).onEach {
                when(it) {
                    is Resource.Error -> {
                        _gameState.value = _gameState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }
                    is Resource.Loading -> {
                        _gameState.value = _gameState.value.copy(
                            loading = true,
                            error = it.message,
                            data = it.data
                        )
                    }
                    is Resource.Success -> {
                        _gameState.value = _gameState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun getGameById(id: String) {
        screenModelScope.launch {
            useCase.getGameById(id, authSettings.getUserInfo().token).onEach {
                when(it) {
                    is Resource.Error -> {
                        _singleGameState.value = _singleGameState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }
                    is Resource.Loading -> {
                        _singleGameState.value = _singleGameState.value.copy(
                            loading = true,
                            error = it.message,
                            data = it.data
                        )
                    }
                    is Resource.Success -> {
                        _singleGameState.value = _singleGameState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun initSingleGame(id: String) {
        if(_singleGameState.value.data==null) {
            getGameById(id)
        }
    }
}