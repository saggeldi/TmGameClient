import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension
import com.game.tm.App
import com.game.tm.components.Splash
import com.game.tm.core.SettingsRepository
import com.russhwolf.settings.PreferencesSettings
import org.jetbrains.compose.resources.painterResource
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.splash
import java.util.prefs.Preferences



fun main() = application {
    val settingsRepository: SettingsRepository by lazy {
        val preferences = Preferences.userRoot()
        val settings = PreferencesSettings(preferences)
        SettingsRepository(settings)
    }

    val loading = remember {
        mutableStateOf(true)
    }

    if(loading.value) {
       Splash {
           loading.value = false
       }
    } else {
        Window(
            title = "TmGame",
            state = rememberWindowState(position = WindowPosition(Alignment.Center), placement = WindowPlacement.Maximized),
            onCloseRequest = ::exitApplication,
        ) {
            App(
                settings = settingsRepository.mySettings,
                onClearSettings = { settingsRepository.clear() }
            )
        }
    }



}