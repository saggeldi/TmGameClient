package com.game.tm.features.profile.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocalization
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.game.tm.state.LocalAppLanguage
import com.game.tm.state.Routes
import com.game.tm.theme.LocalThemeIsDark
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.app_name
import tmgame.composeapp.generated.resources.dark
import tmgame.composeapp.generated.resources.english
import tmgame.composeapp.generated.resources.games
import tmgame.composeapp.generated.resources.language
import tmgame.composeapp.generated.resources.light
import tmgame.composeapp.generated.resources.russian
import tmgame.composeapp.generated.resources.settings
import tmgame.composeapp.generated.resources.system_mod
import tmgame.composeapp.generated.resources.theme
import tmgame.composeapp.generated.resources.turkmen
import java.util.Locale

object ProfileTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(Res.drawable.settings)
            val title = stringResource(Res.string.settings)
            val id = Routes.SETTING
            return TabOptions(
                id,
                icon = icon,
                title = title
            )
        }

    @Composable
    override fun Content() {
        ProfileScreen()
    }

}

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    items: List<String>,
    onSelect: (Int) -> Unit
) {
    val expanded = remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background.copy(alpha = 0.4f),
                RoundedCornerShape(12.dp)
            ).border(0.3.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge
        )
        OutlinedButton(
            onClick = {
                expanded.value = expanded.value.not()
            }
        ) {
            Text(title)
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                }
            ) {
                repeat(items.size) { index ->
                    DropdownMenuItem(
                        text = {
                            Text(items[index])
                        },
                        modifier = Modifier,
                        onClick = {
                            expanded.value = false
                            onSelect(index)
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun ProfileScreen() {
    val isDark = LocalThemeIsDark.current
    val isSystem = isSystemInDarkTheme()
    val language = LocalAppLanguage.current
    Column(
        Modifier.verticalScroll(rememberScrollState()).fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth()
                .padding(top = 22.dp, bottom = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material.Text(
                text = stringResource(Res.string.settings),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            )
        }
        SettingsItem(
            title = stringResource(Res.string.theme),
            items = listOf(
                stringResource(Res.string.light),
                stringResource(Res.string.dark),
                stringResource(Res.string.system_mod),
            ),
            onSelect = { value->
                when(value) {
                    0 -> {
                        isDark.value = false
                    }
                    1 -> {
                        isDark.value = true
                    }
                    2 -> {
                        isDark.value = isSystem
                    }
                }
            }
        )

        SettingsItem(
            title = stringResource(Res.string.language),
            items = listOf(
                stringResource(Res.string.turkmen),
                stringResource(Res.string.english),
                stringResource(Res.string.russian),
            ),
            onSelect = { value->
                when(value) {
                    0 -> {
                        language.value = "tm"
                    }
                    1 -> {

                    }
                    2 -> {
                        language.value = "ru"
                    }
                }
            }
        )
    }
}