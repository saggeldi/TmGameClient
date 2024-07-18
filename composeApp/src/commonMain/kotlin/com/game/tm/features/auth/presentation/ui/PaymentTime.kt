package com.game.tm.features.auth.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.game.tm.state.LocalPaymentState
import com.game.tm.state.LocalStrings

@Composable
fun PaymentTime(
    modifier: Modifier = Modifier,
    actions: @Composable () -> Unit = {}
) {
    val strings = LocalStrings.current
    val payment = LocalPaymentState.current
    payment.value?.let {
        Row(
            modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.weight(1f)) {
                actions()
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(
                    vertical = 12.dp,
                    horizontal = 16.dp
                ).background(
                    color = MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.4f
                    ),
                    shape = RoundedCornerShape(8.dp)
                ).padding(6.dp)
            ) {
                Text(
                    text = it.user.fullname,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.W500
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${strings.paymentDays} ${it.days} ${strings.days}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.W500
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

}