package dev.vicart.masterauth.ui.component.sheet

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import dev.vicart.masterauth.model.AddKeyRequest
import dev.vicart.masterauth.ui.composable.rememberBarcodeScanning
import dev.vicart.masterauth.ui.screen.AddKeyScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddKeyBottomSheet(
    onDismissRequest: () -> Unit,
    addKeyFromQRCode: (String) -> Unit,
    addKeyFromKeyboard: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        val barcodeScanning = rememberBarcodeScanning()
        val coroutineScope = rememberCoroutineScope()
        ListItem(
            headlineContent = { Text("Import by QR code") },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ),
            leadingContent = {
                Icon(Icons.Default.QrCodeScanner, null)
            },
            modifier = Modifier.clickable {
                coroutineScope.launch {
                    val value = barcodeScanning.scanQrCode()
                    value?.let {
                        addKeyFromQRCode(it)
                    }
                }
            }
        )
        ListItem(
            headlineContent = { Text("Enter your key manually") },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ),
            leadingContent = {
                Icon(Icons.Default.Keyboard, null)
            },
            modifier = Modifier.clickable {
                addKeyFromKeyboard()
            }
        )
    }
}