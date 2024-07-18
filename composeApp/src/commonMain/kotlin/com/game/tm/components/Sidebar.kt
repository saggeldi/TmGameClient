package com.game.tm.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.game.tm.features.auth.data.entity.payment.CheckPaymentResponse
import com.game.tm.features.auth.presentation.ui.Auth
import com.game.tm.features.auth.presentation.viewmodel.AuthSettings
import com.game.tm.features.category.presentation.ui.CategoryTab
import com.game.tm.features.game.presentation.ui.GameTab
import com.game.tm.features.profile.presentation.ui.PricingScreen
import com.game.tm.features.profile.presentation.ui.ProfileTab
import com.game.tm.state.LocalAppLanguage
import com.game.tm.state.LocalStrings
import com.game.tm.theme.LocalThemeIsDark
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.ic_dark_mode
import tmgame.composeapp.generated.resources.ic_light_mode
import tmgame.composeapp.generated.resources.logo

@Composable
fun ColumnScope.TabItem(tab: Tab) {
    val current = LocalTabNavigator.current
    SidebarButton(
        text = tab.options.title,
        icon = tab.options.icon,
        selected = current.current == tab
    ) {
        current.current = tab
    }
}

@Composable
fun Sidebar(modifier: Modifier, navigator: Navigator, data: CheckPaymentResponse) {
    Column(
        modifier.fillMaxHeight().width(250.dp).background(
            color = MaterialTheme.colorScheme.background.copy(alpha = 0.2f)
        ).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AppLogo()
        Spacer(Modifier.height(6.dp))
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            TabItem(GameTab)
            TabItem(CategoryTab)
            TabItem(ProfileTab)
        }
        ProfileItem(navigator)

    }
}

@OptIn(ExperimentalResourceApi::class, InternalResourceApi::class)
@Composable
fun ProfileItem(navigator: Navigator) {
    val strings = LocalStrings.current
    val language = LocalAppLanguage.current
    val authSettings = koinInject<AuthSettings>()
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            Modifier.fillMaxWidth().background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            ).clickable { navigator.push(PricingScreen()) }.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(40.dp).border(1.dp, MaterialTheme.colorScheme.onPrimary, CircleShape).padding(6.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(Modifier.width(6.dp))
            Column {
                Text(
                    authSettings.getUserInfo().fullname,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    authSettings.getUserInfo().clientType,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        TextButton(
            onClick = {
                authSettings.logout()
                navigator.replaceAll(Auth())
            },
            modifier = Modifier.fillMaxWidth()
        ) {
                Text(
                    strings.logout,
                    color = MaterialTheme.colorScheme.primary
                )

        }
    }
}

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    val isDark by LocalThemeIsDark.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            contentScale = ContentScale.FillBounds
        )
        Column {
            Text(
                text = LocalStrings.current.app_name,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            Text(
                text = LocalStrings.current.app_description,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Composable
fun ModeSwitch() {
    var isDark by LocalThemeIsDark.current
    val icon = remember(isDark) {
        if (isDark) Res.drawable.ic_light_mode
        else Res.drawable.ic_dark_mode
    }

    ElevatedButton(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
        onClick = { isDark = !isDark },
        content = {
            Icon(vectorResource(icon), contentDescription = null)
        }
    )
}

@Composable
fun SidebarButton(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    text: String,
    icon: Painter?,
    onClick: () -> Unit
) {
    val color =
        if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(vertical = 6.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(selected) {
            Box(
                Modifier
                    .width(5.dp)
                    .height(25.dp)
                    .background(color, RoundedCornerShape(8.dp))
                    .shadow(4.dp, RoundedCornerShape(8.dp), ambientColor = color)
            )
            Spacer(Modifier.width(26.dp))
        }
        icon?.let {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(Modifier.width(6.dp))
        Text(
            text, color = color, style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )
        )
    }
}