package dev.vicart.masterauth.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.vicart.kotp.common.HashAlgorithm
import dev.vicart.masterauth.R
import dev.vicart.masterauth.ui.component.control.BackButton
import dev.vicart.masterauth.ui.component.control.CodeLengthSlider
import dev.vicart.masterauth.ui.component.control.DropdownSelector
import dev.vicart.masterauth.ui.component.control.Stepper
import dev.vicart.masterauth.ui.composable.LocalNavController
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
            val issuer by vm.issuer.observeAsState("")
            TextField(
                value = issuer,
                onValueChange = { vm.issuer.value = it },
                singleLine = true,
                label = { Text(stringResource(R.string.add_key_issuer)) },
                modifier = Modifier.fillMaxWidth()
            )
            val accountName by vm.accountName.observeAsState("")
            TextField(
                value = accountName,
                onValueChange = { vm.accountName.value = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.add_key_account_name)) }
            )
            val key by vm.key.observeAsState("")
            TextField(
                value = key,
                onValueChange = { vm.key.value = it },
                singleLine = true,
                label = { Text(stringResource(R.string.add_key_key)) },
                modifier = Modifier.fillMaxWidth()
            )
            val codeLength by vm.codeLength.observeAsState(6)
            val period by vm.period.observeAsState(30L)
            val algorithm by vm.algorithm.observeAsState(HashAlgorithm.SHA1)
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
                    Stepper(
                        value = period.toInt(),
                        onValueChange = { vm.period.value = it.toLong() },
                        min = 0,
                        label = { Text(stringResource(R.string.add_key_code_exp)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownSelector(
                        value = algorithm,
                        onValueChanged = { vm.algorithm.value = it },
                        values = HashAlgorithm.entries.toTypedArray(),
                        label = { Text(stringResource(R.string.add_key_algorithm)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = stringResource(R.string.add_key_tip),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            TextButton(
                onClick = { advancedShown = !advancedShown }
            ) {
                Text(
                    text = if(advancedShown) stringResource(R.string.add_key_hide_advanced_options)
                        else stringResource(R.string.add_key_show_advanced_options)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    vm: AddKeyViewModel
) {
    val issuer by vm.issuer.observeAsState("")
    val accountName by vm.accountName.observeAsState("")
    val key by vm.key.observeAsState("")
    val isValid by remember {
        derivedStateOf {
            issuer.isNotBlank() && key.isNotBlank() &&
                    accountName.isNotBlank()
        }
    }
    val navController = LocalNavController.current
    TopAppBar(
        title = { Text("Enter your key") },
        navigationIcon = {
            BackButton()
        },
        actions = {
            IconButton(
                onClick = {
                    vm.addRawKey {
                        navController.popBackStack()
                    }
                },
                enabled = isValid
            ) {
                Icon(Icons.Default.Check, null)
            }
        }
    )
}