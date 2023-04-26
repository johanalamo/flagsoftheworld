package com.alamo.jc_ui_components


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun GenericDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    onConfirm: () -> Unit,
    onConfirmText: String,
    onDismiss: () -> Unit,
    onDismissText: String,
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = onConfirm ) {
                Text(text = onConfirmText)
            }
        },
        modifier = modifier,
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = onDismissText)
            }
        },
//    icon: @Composable (() -> Unit)? = null,
        title = { Text(text = title) },
        text = { Text(text = description ?: "") },
    )
}

