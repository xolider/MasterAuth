package dev.vicart.masterauth.ui.screen

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.vicart.masterauth.R
import dev.vicart.masterauth.activity.MainActivity
import dev.vicart.masterauth.model.AddKeyRequest
import dev.vicart.masterauth.model.OTPCode
import dev.vicart.masterauth.ui.component.dialog.ConfirmDeleteCodeDialog
import dev.vicart.masterauth.ui.component.dialog.ConfirmKeyAddDialog
import dev.vicart.masterauth.ui.component.sheet.AddKeyBottomSheet
import dev.vicart.masterauth.ui.composable.LocalNavController
import dev.vicart.masterauth.ui.viewmodel.CodesListViewModel
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

@Serializable
class CodesListScreen

@Composable
fun CodesListScreenContent(
    vm: CodesListViewModel = viewModel()
) {
    Scaffold(
        topBar = { TopBar() },
        floatingActionButton = { AddFab(vm) }
    ) {
        val otpCodes by vm.otpCodes.observeAsState(emptyList())
        if(otpCodes.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(it).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = otpCodes) {
                    val code by it.collectAsStateWithLifecycle(null)
                    if(code != null) {
                        CodeItem(code!!) {
                            vm.removeKey(code!!.uid)
                        }
                    }
                }
            }
        } else {
            EmptyItem(
                modifier = Modifier.fillMaxSize().padding(it).padding(16.dp)
            )
        }
    }
    RequestForAddingKeyIfNeeded(vm)
}

@Composable
private fun EmptyItem(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {

    }
}

@Composable
private fun RequestForAddingKeyIfNeeded(
    vm: CodesListViewModel
) {
    val activity = LocalContext.current as MainActivity
    val data = remember(activity) { activity.intent.data }
    if(data != null) {
        val request = remember(data) { AddKeyRequest.fromUri(data) }
        if(request != null) {
            ConfirmKeyAddDialog(request) {
                vm.addRawKey(request)
            }
        } else {
            LaunchedEffect(activity) {
                activity.finish()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CodeItem(
    code: OTPCode,
    onDelete: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var menuShown by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.combinedClickable(
            onClick = {
                clipboardManager.setText(AnnotatedString(code.code))
                Toast.makeText(context, "Texte copié", Toast.LENGTH_SHORT).show()
            },
            onLongClick = {
                menuShown = true
            }
        )
    ) {
        Text(
            text = code.issuer,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = code.accountName,
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = with(code.code) {
                    val mid = this.length/2
                    "${substring(0, mid)} ${substring(mid)}"
                },
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            var remainingSeconds by remember(code) { mutableLongStateOf(code.nextEmit) }
            val progress by animateFloatAsState(remainingSeconds.toFloat())
            CircularProgressIndicator(progress = { progress / code.period }, modifier = Modifier.scale(-1f, 1f))
            LaunchedEffect(code) {
                while (true) {
                    delay(1000)
                    remainingSeconds -= 1
                }
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp)
        )
    }
    var deleteCodeDialog by remember { mutableStateOf(false) }
    if(deleteCodeDialog) {
        ConfirmDeleteCodeDialog(
            onDismissRequest = { deleteCodeDialog = false },
            onConfirm = {
                onDelete()
            }
        )
    }
    DropdownMenu(
        expanded = menuShown,
        onDismissRequest = { menuShown = false }
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.codes_list_delete)) },
            onClick = { deleteCodeDialog = true },
            leadingIcon = { Icon(Icons.Default.Delete, null) },
            colors = MenuItemColors(
                textColor = MaterialTheme.colorScheme.error,
                leadingIconColor = MaterialTheme.colorScheme.error,
                trailingIconColor = Color.Unspecified,
                disabledTextColor = Color.Unspecified,
                disabledLeadingIconColor = Color.Unspecified,
                disabledTrailingIconColor = Color.Unspecified
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.app_name)) }
    )
}

@Composable
private fun AddFab(
    vm: CodesListViewModel
) {
    var addExpanded by remember { mutableStateOf(false) }
    if(addExpanded) {
        val navcontroller = LocalNavController.current
        AddKeyBottomSheet(
            onDismissRequest = { addExpanded = false },
            addKeyFromKeyboard = {
                navcontroller.navigate(AddKeyScreen())
                addExpanded = false
            },
            addKeyFromQRCode = {
                vm.addKeyFromQRCode(it)
                addExpanded = false
            }
        )
    }
    FloatingActionButton(
        onClick = { addExpanded = true }
    ) {
        Icon(Icons.Default.Add, null)
    }
}