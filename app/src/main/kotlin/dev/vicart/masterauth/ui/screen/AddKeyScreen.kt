package dev.vicart.masterauth.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.vicart.masterauth.R
import dev.vicart.masterauth.ui.component.control.BackButton
import dev.vicart.masterauth.ui.component.control.CodeLengthSlider
import dev.vicart.masterauth.ui.viewmodel.AddKeyViewModel
import kotlinx.serialization.Serializable

@Serializable
class AddKeyScreen

@Composable
fun AddKeyScreenContent(
    vm: AddKeyViewModel = viewModel()
) {
    Scaffold(
        topBar = { TopBar(vm) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(it).padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            val label by vm.label.observeAsState("")
            TextField(
                value = label,
                onValueChange = { vm.label.value = it },
                singleLine = true,
                label = { Text(stringResource(R.string.codes_list_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            val key by vm.key.observeAsState("")
            TextField(
                value = key,
                onValueChange = { vm.key.value = it },
                singleLine = true,
                label = { Text(stringResource(R.string.codes_list_key)) },
                modifier = Modifier.fillMaxWidth()
            )
            val codeLength by vm.codeLength.observeAsState(6)
            val period by vm.period.observeAsState(30L)
            var advancedShown by remember { mutableStateOf(false) }
            AnimatedVisibility(
                visible = advancedShown
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CodeLengthSlider(
                        value = codeLength,
                        onValueChange = { vm.codeLength.value = it }
                    )
                    OutlinedTextField(
                        value = period.toString(),
                        onValueChange = {
                            try {
                                vm.period.value = it.toLong()
                            } catch (_: Exception) {}
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = { Text("Validity of code (in seconds)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            TextButton(
                onClick = { advancedShown = !advancedShown }
            ) {
                Text("Show advanced options")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    vm: AddKeyViewModel
) {
    TopAppBar(
        title = { Text("Enter your key") },
        navigationIcon = {
            BackButton()
        },
        actions = {
            IconButton(
                onClick = vm::addRawKey
            ) {
                Icon(Icons.Default.Check, null)
            }
        }
    )
}