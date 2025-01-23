package dev.vicart.masterauth.ui.composable

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = staticCompositionLocalOf<NavHostController> { throw IllegalStateException("No nav controller") }