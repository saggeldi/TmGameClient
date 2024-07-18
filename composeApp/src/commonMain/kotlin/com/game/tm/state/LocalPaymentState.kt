package com.game.tm.state

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.game.tm.features.auth.data.entity.payment.CheckPaymentResponse

val LocalPaymentState = compositionLocalOf {
    mutableStateOf<CheckPaymentResponse?>(null)
}