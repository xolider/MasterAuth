package dev.vicart.masterauth.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import dev.vicart.masterauth.ui.composable.LocalNavController
import dev.vicart.masterauth.ui.screen.AddKeyScreen
import dev.vicart.masterauth.ui.screen.AddKeyScreenContent
import dev.vicart.masterauth.ui.screen.CodesListScreen
import dev.vicart.masterauth.ui.screen.CodesListScreenContent
import dev.vicart.masterauth.ui.theme.AppTheme

@Composable
fun App() = AppTheme {
    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = CodesListScreen(),
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End) }
        ) {
            composable<CodesListScreen> {
                CodesListScreenContent()
            }
            composable<AddKeyScreen> {
                AddKeyScreenContent()
            }
        }
    }
}