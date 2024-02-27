package com.alamo.ui_countrydetails.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import coil.annotation.ExperimentalCoilApi
import com.alamo.jc_ui_components.PersonalizedIcons
import com.alamo.ui_countrydetails.ui.CountryDetailsEvents
import com.alamo.ui_countrydetails.ui.CountryDetailsState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun CountryDetailsScaffold(
    state: CountryDetailsState,
    events: (CountryDetailsEvents) -> Unit,
    navigateBack: () -> Unit
) {
    val snackbarHostState = SnackbarHostState()
    Scaffold(topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }, title = {
            Text(text = state.data?.name.orEmpty())
        }, actions = {

            if (state.data?.isFavorite == true) {
                IconButton(onClick = { events(CountryDetailsEvents.RemoveCountryFromFavorites(state.data.codeISO3)) }) {
                    Icon(imageVector = PersonalizedIcons.IsFavorite, contentDescription = null)
                }
            } else {
                IconButton(onClick = { events(CountryDetailsEvents.AddCountryToFavorites(state.data?.codeISO3 ?: ""))}) {
                    Icon(imageVector = PersonalizedIcons.IsNotFavorite, contentDescription = null)
                }
            }

        })
    }, content = { paddingValues ->


        Column(modifier = Modifier.padding(paddingValues)) {
            CountryDetails(state = state, events = events, snackbarHostState = snackbarHostState)
        }
    }, snackbarHost = {
        SnackbarHost(snackbarHostState) { snackbarData ->
            Snackbar(
                snackbarData, modifier = Modifier.padding(16.dp)
            )
        }
    })
}
