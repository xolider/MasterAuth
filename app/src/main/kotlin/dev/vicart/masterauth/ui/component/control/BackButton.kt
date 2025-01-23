package dev.vicart.masterauth.ui.component.control

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import dev.vicart.masterauth.ui.composable.LocalNavController

@Composable
fun BackButton() {
    val navController = LocalNavController.current
    IconButton(
        onClick = navController::popBackStack
    ) {
        Icon(Icons.AutoMirrored.Default.ArrowBack, null)
    }
}