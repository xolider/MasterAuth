package dev.vicart.masterauth.ui.component.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import dev.vicart.masterauth.R

@Composable
fun ConfirmDeleteCodeDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.confirm_delete_code_title)) },
        text = { Text(
            text = stringResource(R.string.confirm_delete_code_text),
            textAlign = TextAlign.Justify
        ) },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors().copy(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(stringResource(android.R.string.ok))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest
            ) {
                Text(stringResource(android.R.string.cancel))
            }
        },
        icon = {
            Icon(
                Icons.Default.Warning,
                null
            )
        }
    )
}