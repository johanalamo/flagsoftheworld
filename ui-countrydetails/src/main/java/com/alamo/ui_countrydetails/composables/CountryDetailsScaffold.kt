package com.alamo.ui_countrydetails.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailsScaffold(countryCode: String, onBackClicked: () -> Unit) {
    val snackbarHostState = SnackbarHostState()
    Scaffold(topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = { onBackClicked()}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }, title = {
            Text(text = countryCode)
        }, actions = {
            IconButton(onClick = {  }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
            }
        })
    }, content = { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text("information about venezuela")
        }
    }, snackbarHost = {
        SnackbarHost(snackbarHostState) { snackbarData ->
            Snackbar(
                snackbarData, modifier = Modifier.padding(16.dp)
            )
        }
    })
}
