package com.alamo.ui_countrylist.composables

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ShowTemporalSnackbar(snackbarHostState: SnackbarHostState, message: String, closeAction: () -> Unit) {
    val scope = rememberCoroutineScope()
    scope.launch {
        val job = scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long,
                withDismissAction = true,
            )
        }
        delay(3000)
        closeAction()
        job.cancel()
    }
}