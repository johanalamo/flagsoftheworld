package com.alamo.jc_ui_components.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alamo.jc_ui_components.GenericDialog

@Composable
@Preview
fun GenericDialogPreview() {
    GenericDialog(
        modifier = Modifier,
        title = "Error dialog title",
        description = "Error dialog description",
        onConfirm = {},
        onConfirmText = "Retry",
        onDismiss = {},
        onDismissText = "Cancel",
    )
}

