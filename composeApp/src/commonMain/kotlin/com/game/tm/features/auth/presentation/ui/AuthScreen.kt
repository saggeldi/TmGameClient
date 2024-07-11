package com.game.tm.features.auth.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.game.tm.Test
import com.game.tm.components.GlassBackground
import com.game.tm.components.MainScreen
import com.game.tm.features.auth.presentation.viewmodel.AuthViewModel
import com.game.tm.state.LocalStrings
import com.game.tm.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.auth

class Auth : Screen {
    @Composable
    override fun Content() {
        AppTheme {
            AuthScreen()
        }
    }

}

@Composable
fun AuthScreen() {

    val index = remember {
        mutableStateOf(0)
    }
    val strings = LocalStrings.current
    val nav = LocalNavigator.currentOrThrow
    val authViewModel = nav.koinNavigatorScreenModel<AuthViewModel>()

    GlassBackground {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()).weight(1f).wrapContentHeight().padding(30.dp).background(
                    color = MaterialTheme.colorScheme.background.copy(
                        alpha = 0.6f
                    ),
                    shape = RoundedCornerShape(22.dp)
                ).padding(22.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedContent(index.value) {
                    when (it) {
                        0 -> {
                            Login(authViewModel = authViewModel)
                        }
                        1 -> {
                            CreateAccount(
                                authViewModel = authViewModel
                            )
                        }
                        else -> {
                            PaymentScreen()
                        }
                    }
                }
                if (index.value<2){
                    Spacer(Modifier.height(32.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AuthButton(
                            loading = authViewModel.signInState.value.loading,
                            modifier = Modifier.weight(1f),
                            text = strings.sign_in,
                            selected = index.value == 0
                        ) {
                            if (index.value != 0){
                                index.value = 0
                            } else {
                                authViewModel.signIn(
                                    onSuccess = {user->
                                        if (user.token.isNotEmpty()){
                                            nav?.replace(MainScreen())
                                        } else {
                                            index.value = 2
                                        }},
                                    onPayment = {
                                        index.value = 2
                                    }
                                )
                            }
                        }
                        Spacer(Modifier.width(22.dp))
                        AuthButton(
                            loading = authViewModel.signUpState.value.loading,
                            modifier = Modifier.weight(1f),
                            text = strings.sign_up,
                            selected = index.value == 1
                        ) {
                            if (index.value != 1){
                                index.value = 1
                            } else {
                                authViewModel.signUp(
                                    onSuccess = { newUser->
                                        index.value = 0
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                val test = Test()

                Image(
                    painter = painterResource(Res.drawable.auth),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Inside
                )
            }

        }
    }
}

@Composable
fun Login(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val form = authViewModel.signInForm
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            LocalStrings.current.sign_in,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
        Spacer(Modifier.height(22.dp))
        AuthInput(
            value = form.value.username,
            onChange = {
                authViewModel.changeSignInForm(form.value.copy(username = it))
            },
            icon = Icons.Outlined.Person,
            label = LocalStrings.current.username
        )

        Spacer(Modifier.height(16.dp))

        AuthInput(
            value = form.value.password,
            onChange = {
                authViewModel.changeSignInForm(form.value.copy(password = it))
            },
            icon = Icons.Outlined.Build,
            label = LocalStrings.current.password
        )
    }
}

@Composable
fun CreateAccount(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val form = authViewModel.signUpForm
    val strings = LocalStrings.current
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            strings.sign_up,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
        Spacer(Modifier.height(22.dp))
        AuthInput(
            value = form.value.username,
            onChange = {
                authViewModel.changeForm(
                    form = form.value.copy(
                        username = it
                    )
                )
            },
            icon = Icons.Outlined.Person,
            label = strings.username
        )

        Spacer(Modifier.height(16.dp))

        AuthInput(
            value = form.value.password,
            onChange = {
                authViewModel.changeForm(
                    form = form.value.copy(
                        password = it
                    )
                )
            },
            icon = Icons.Outlined.Build,
            label = strings.password
        )

        Spacer(Modifier.height(16.dp))

        AuthInput(
            value = form.value.fullname,
            onChange = {
                authViewModel.changeForm(
                    form = form.value.copy(
                        fullname = it
                    )
                )
            },
            icon = Icons.Outlined.AccountCircle,
            label = strings.fullName
        )

        Spacer(Modifier.height(16.dp))

        AuthInput(
            value = form.value.phone,
            maxLength = 8,
            prefix = {
                Text("+993")
            },
            onChange = {
                authViewModel.changeForm(
                    form = form.value.copy(
                        phone = it
                    )
                )
            },
            icon = Icons.Outlined.Phone,
            label = strings.phone
        )

        Spacer(Modifier.height(16.dp))

        AuthInput(
            value = form.value.email,
            onChange = {
                authViewModel.changeForm(
                    form = form.value.copy(
                        email = it
                    )
                )
            },
            icon = Icons.Outlined.Email,
            label = strings.email
        )




    }
}

@Composable
fun AuthInput(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: ImageVector,
    maxLength: Int = 0,
    prefix: @Composable ()-> Unit = {},
    isError: Boolean = value.isEmpty() || (maxLength!=0 && value.length<maxLength),
    onChange: (String) -> Unit
) {
    val strings = LocalStrings.current
    OutlinedTextField(
        value = value,
        onValueChange = {
            if(it.length<=maxLength || maxLength==0) {
                onChange(it)
            }
        },
        modifier = modifier.fillMaxWidth(),
        prefix = prefix,
        supportingText = {
            if(isError) {
                Text(strings.errorField.replace("%s", label), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
            }
        },
        label = {
            Text(label, style = MaterialTheme.typography.bodyMedium)
        },
        isError = isError,
        trailingIcon = {
            if(isError) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = value,
                    modifier = Modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        leadingIcon = {
            Icon(
                icon,
                contentDescription = value,
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    )

}

@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    loading: Boolean = false,
    text: String,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(22.dp)
    Box(
        modifier = modifier.background(
            brush = Brush.horizontalGradient(
                colors = if (selected)
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.error,
                    )
                else
                    listOf(
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    )
            ),
            shape = shape
        ).clip(shape).clickable { onClick() }.padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        if(loading) {
            CircularProgressIndicator(modifier = Modifier.size(25.dp), color = MaterialTheme.colorScheme.onPrimary)
        } else {
            Text(text, color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface)
        }
    }
}