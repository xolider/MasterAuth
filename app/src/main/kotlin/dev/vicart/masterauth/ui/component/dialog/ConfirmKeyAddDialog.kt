package dev.vicart.masterauth.ui.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import dev.vicart.masterauth.R
import dev.vicart.masterauth.model.AddKeyRequest

@Composable
fun ConfirmKeyAddDialog(
    request: AddKeyRequest,
    onConfirm: () -> Unit
) {
    var opened by remember { mutableStateOf(true) }
    if(opened) {
        AlertDialog(
            onDismissRequest = { opened = false },
            title = { Text(stringResource(R.string.confirm_key_add_title)) },
            text = { Text(stringResource(
                if(request.issuer.isNotBlank()) R.string.confirm_key_add_text_with_issuer
                    else R.string.confirm_key_add_text_without_issuer, request.accountName, request.issuer)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm()
                        opened = false
                    }
                ) {
                    Text(stringResource(android.R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { opened = false }
                ) {
                    Text(stringResource(android.R.string.cancel))
                }
            }
        )
    }
}