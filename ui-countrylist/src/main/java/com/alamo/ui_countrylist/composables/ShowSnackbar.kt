package com.alamo.ui_countrylist.composables

import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alamo.ui_countrylist.R

@Composable
fun ShowSnackbar(message: String, closeAction: () -> Unit) {
    Snackbar(
        action = {
            Button(
                onClick = { closeAction() }
            ) {
                Text(text = stringResource(id = R.string.close))
            }
        }
    ) {
        Text(text = message)
    }
}