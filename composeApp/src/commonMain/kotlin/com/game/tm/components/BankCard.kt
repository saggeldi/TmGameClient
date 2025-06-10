import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BankCardScreen(
    onPayClick: () -> Unit,
    loading: Boolean
) {
    var cardNumber by remember { mutableStateOf("") }
    var cardHolderName by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bank Card UI
        BankCard(
            cardNumber = cardNumber,
            cardHolderName = cardHolderName,
            expiryDate = expiryDate
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Card Information Form
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Kard Maglumatlary",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Card Number Field
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { value ->
                        val digits = value.filter { it.isDigit() }
                        if (digits.length <= 16) {
                            cardNumber = formatCardNumber(digits)
                        }
                    },
                    label = { Text("Kard Nomeri") },
                    placeholder = { Text("1234 5678 9012 3456") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Cardholder Name Field
                OutlinedTextField(
                    value = cardHolderName,
                    onValueChange = { cardHolderName = it.uppercase() },
                    label = { Text("Kard eýesi") },
                    placeholder = { Text("Amanow Aman") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Expiry Date Field
                    OutlinedTextField(
                        value = expiryDate,
                        onValueChange = { value ->
                            val digits = value.filter { it.isDigit() }
                            if (digits.length <= 4) {
                                expiryDate = formatExpiryDate(digits)
                            }
                        },
                        label = { Text("Kard Möhleti") },
                        placeholder = { Text("MM/YY") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    // CVV Field
                    OutlinedTextField(
                        value = cvv,
                        onValueChange = { value ->
                            val digits = value.filter { it.isDigit() }
                            if (digits.length <= 3) {
                                cvv = digits
                            }
                        },
                        label = { Text("CVV") },
                        placeholder = { Text("123") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Submit Button
                Button(
                    onClick = { onPayClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    if(loading) {
                        CircularProgressIndicator()
                    } else {
                        Text(
                            text = "Tölemek",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BankCard(
    cardNumber: String,
    cardHolderName: String,
    expiryDate: String
) {
    Box(
        modifier = Modifier
            .width(320.dp)
            .height(200.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Card Top Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "BANK",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                // Card Chip
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                            RoundedCornerShape(4.dp)
                        )
                )
            }

            // Card Number
            Text(
                text = if (cardNumber.isNotEmpty()) cardNumber else "•••• •••• •••• ••••",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp
            )

            // Card Bottom Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "KARD EÝESI",
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                        fontSize = 10.sp
                    )
                    Text(
                        text = if (cardHolderName.isNotEmpty()) cardHolderName else "ADYŇYZ",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "MÖHLETI",
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                        fontSize = 10.sp
                    )
                    Text(
                        text = if (expiryDate.isNotEmpty()) expiryDate else "MM/YY",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// Helper function to format card number with spaces
fun formatCardNumber(cardNumber: String): String {
    return cardNumber.chunked(4).joinToString(" ")
}

// Helper function to format expiry date with slash
fun formatExpiryDate(date: String): String {
    return when (date.length) {
        0, 1 -> date
        2 -> "${date}/"
        else -> "${date.substring(0, 2)}/${date.substring(2)}"
    }
}