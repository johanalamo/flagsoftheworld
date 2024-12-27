package com.alamo.ui_countrydetails.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.alamo.jc_ui_components.BottomBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun SettingsScaffold(
    navigateBack: () -> Unit,
    navigateToMapScreen: () -> Unit,
    navigateToCountryListScreen: () -> Unit,
) {
    val snackbarHostState = SnackbarHostState()
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navigateBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            title = {
                Text(text = "Settings")
            },
        )
    }, content = { paddingValues ->

        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Warning, "Settings is currently not available")
            Text(
                text = "Settings is currently not available",
                fontWeight = FontWeight.Bold
            )
        }
    }, snackbarHost = {
        SnackbarHost(snackbarHostState) { snackbarData ->
            Snackbar(
                snackbarData, modifier = Modifier.padding(16.dp)
            )
        }
    },
        bottomBar = {
            BottomBar(
                navigateToCountryListScreen = navigateToCountryListScreen,
                navigateToMapScreen = navigateToMapScreen,
            )
        }
    )
}
