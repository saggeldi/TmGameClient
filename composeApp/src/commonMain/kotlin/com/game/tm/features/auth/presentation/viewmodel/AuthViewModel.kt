package com.game.tm.features.auth.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import com.game.tm.core.Resource
import com.game.tm.features.auth.data.entity.PayRequest
import com.game.tm.features.auth.data.entity.SignInRequest
import com.game.tm.features.auth.data.entity.SignInResponse
import com.game.tm.features.auth.data.entity.SignUpRequest
import com.game.tm.features.auth.data.entity.SignUpResponse
import com.game.tm.features.auth.domain.usecase.AuthUseCase
import com.game.tm.features.auth.presentation.state.PayState
import com.game.tm.features.auth.presentation.state.SignInState
import com.game.tm.features.auth.presentation.state.SignUpState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AuthViewModel(
    private val useCase: AuthUseCase,
    private val authSettings: AuthSettings
) : ScreenModel {
    private val job = SupervisorJob()
    private val coroutineContext: CoroutineContext = job + Dispatchers.IO
    private val viewModelScope = CoroutineScope(coroutineContext)

    var signUpState = mutableStateOf(SignUpState())
        private set

    var signUpForm = mutableStateOf(SignUpRequest())
        private set

    var signInForm = mutableStateOf(SignInRequest())
        private set

    var signInState = mutableStateOf(SignInState())
        private set

    var payState = mutableStateOf(PayState())
        private set

    var key = mutableStateOf("")
        private set

    fun changeKey(k: String) {
        key.value = k
    }

    fun changeForm(form: SignUpRequest) {
        signUpForm.value = form
    }

    fun changeSignInForm(form: SignInRequest) {
        signInForm.value = form
    }

    fun signUp(onSuccess: (SignUpResponse) -> Unit) {
        viewModelScope.launch {
            useCase.signUp(
                body = signUpForm.value
            ).onEach {
                when (it) {
                    is Resource.Error -> {
                        signUpState.value = signUpState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }

                    is Resource.Loading -> {
                        signUpState.value = signUpState.value.copy(
                            loading = true,
                            error = it.message,
                            data = it.data
                        )
                    }

                    is Resource.Success -> {
                        signUpState.value = signUpState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                        it.data?.let { it1 ->
                            onSuccess(it1)
                            signInForm.value = SignInRequest(it1.username, it1.password)
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    fun signIn(onSuccess: (SignInResponse) -> Unit, onPayment: () -> Unit) {
        viewModelScope.launch {
            useCase.signIn(
                body = signInForm.value
            ).onEach {
                when (it) {
                    is Resource.Error -> {
                        println("Code: " + it.code)
                        signInState.value = signInState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }

                    is Resource.Loading -> {
                        signInState.value = signInState.value.copy(
                            loading = true,
                            error = it.message,
                            data = it.data
                        )
                    }

                    is Resource.Success -> {
                        signInState.value = signInState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                        it.data?.let { it1 ->
                            if (it1.clientType.isNotEmpty() && it1.clientType.lowercase()
                                    .contains("none").not()
                            ) {
                                onSuccess(it1)
                            } else {
                                onPayment()
                            }
                            authSettings.saveUser(it1)
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    fun pay(onSuccess: (SignInResponse) -> Unit = {}) {
        viewModelScope.launch {
            useCase.payWithKey(
                body = PayRequest(
                    key = key.value,
                    token = authSettings.getUserInfo().token
                )
            ).onEach {
                when (it) {
                    is Resource.Error -> {
                        println("Code: " + it.code)
                        payState.value = payState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }

                    is Resource.Loading -> {
                        payState.value = payState.value.copy(
                            loading = true,
                            error = it.message,
                            data = it.data
                        )
                    }

                    is Resource.Success -> {
                        payState.value = payState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                        it.data?.let { it1 ->
                            onSuccess(it1)
                            authSettings.saveUser(it1)
                        }
                    }
                }
            }.launchIn(this)
        }
    }
}