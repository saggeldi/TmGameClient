package com.game.tm.features.server.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.game.tm.core.Resource
import com.game.tm.features.auth.presentation.viewmodel.AuthSettings
import com.game.tm.features.server.domain.usecase.ServerUseCase
import com.game.tm.features.server.presentation.state.ServerState
import com.game.tm.features.server.presentation.state.TeamSpeakState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ServerViewmodel(
    private val useCase: ServerUseCase,
    private val authSettings: AuthSettings
): ScreenModel {
    private val _teamSpeak = MutableStateFlow(TeamSpeakState())
    val teamSpeak = _teamSpeak.asStateFlow()

    private val _serverState = MutableStateFlow(ServerState())
    val serverState = _serverState.asStateFlow()

    fun getTeamSpeak() {
        screenModelScope.launch {
            useCase.getTeamSpeak().onEach {
                when(it) {
                    is Resource.Loading -> {
                        _teamSpeak.value = _teamSpeak.value.copy(
                            loading = true,
                            error = it.message,
                            data = it.data
                        )
                    }
                    is Resource.Error -> {
                        _teamSpeak.value = _teamSpeak.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }
                    is Resource.Success -> {
                        _teamSpeak.value = _teamSpeak.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun initTeamSpeak() {
        if(_teamSpeak.value.data.isNullOrEmpty()) {
            getTeamSpeak()
        }
    }


    fun getServers(location: String) {
        screenModelScope.launch {
            useCase.getServers(location).onEach {
                when(it) {
                    is Resource.Loading -> {
                        _serverState.value = _serverState.value.copy(
                            loading = true,
                            error = it.message,
                            data = it.data
                        )
                    }
                    is Resource.Error -> {
                        _serverState.value = _serverState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }
                    is Resource.Success -> {
                        _serverState.value = _serverState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data?.filter { v-> authSettings.checkAccess(v.type) }
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}