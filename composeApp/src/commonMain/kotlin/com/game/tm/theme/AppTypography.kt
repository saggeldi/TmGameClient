package com.game.tm.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import tmgame.composeapp.generated.resources.Res
import tmgame.composeapp.generated.resources.bold
import tmgame.composeapp.generated.resources.extrabold
import tmgame.composeapp.generated.resources.extralight
import tmgame.composeapp.generated.resources.light
import tmgame.composeapp.generated.resources.medium
import tmgame.composeapp.generated.resources.regular
import tmgame.composeapp.generated.resources.semibold

@Composable
fun PoppinsFontFamily() = FontFamily(
    Font(Res.font.light, weight = FontWeight.Light),
    Font(Res.font.regular, weight = FontWeight.Normal),
    Font(Res.font.medium, weight = FontWeight.Medium),
    Font(Res.font.semibold, weight = FontWeight.SemiBold),
    Font(Res.font.bold, weight = FontWeight.Bold),
    Font(Res.font.extrabold, weight = FontWeight.ExtraBold),
    Font(Res.font.extralight, weight = FontWeight.ExtraLight),
)