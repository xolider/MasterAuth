package dev.vicart.masterauth.ui.component.control

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun Stepper(
    value: Int,
    onValueChange: (Int) -> Unit,
    step: Int = 1,
    min: Int = Int.MIN_VALUE,
    max: Int = Int.MAX_VALUE,
    label: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        if(label != null) {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.titleMedium
            ) {
                label()
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            FilledIconButton(
                onClick = {
                    if(value - step >= min) {
                        onValueChange(value - step)
                    }
                },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(Icons.Default.Remove, null)
            }
            BasicTextField(
                value = value.toString(),
                onValueChange = {
                    try {
                        val intValue = it.toInt()
                        if(intValue > max) {
                            onValueChange(max)
                        } else if(intValue < min) {
                            onValueChange(min)
                        } else {
                            onValueChange(intValue)
                        }
                    } catch (_: Exception) {}
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.width(IntrinsicSize.Min),
                decorationBox = {
                    Box(
                        modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.outlineVariant, MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.small)
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                    ) {
                        it()
                    }
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
            FilledIconButton(
                onClick = {
                    if(value + step <= max) {
                        onValueChange(value + step)
                    }
                },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(Icons.Default.Add, null)
            }
        }
    }
}