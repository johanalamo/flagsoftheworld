package com.alamo.ui_countrylist.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.alamo.ui_countrylist.ui.CountryListEvents
import com.alamo.ui_countrylist.ui.CountryListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScaffold(
    state: CountryListState,
    events: (CountryListEvents) -> Unit,
    navigateToCountryDetailsScreen: (String) -> Unit = { null },
) {
    val snackbarHostState = SnackbarHostState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
                        textStyle = TextStyle.Default,
                        value = state.countryNameToSearch,
                        maxLines = 1,
                        singleLine = true,
                        onValueChange = {
                            events(CountryListEvents.UpdateCountryToSearch(it))
                        },
                        trailingIcon = {
                            IconButton(onClick = { events(CountryListEvents.UpdateCountryToSearch("")) }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                            }
                        },
                        placeholder = { Text("Introduce a country") }
                    )
                },
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                CountryList(state = state, events = events, snackbarHostState = snackbarHostState, navigateToCountryDetailsScreen = navigateToCountryDetailsScreen)
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { snackbarData ->
                Snackbar(
                    snackbarData,
                    modifier = Modifier.padding(16.dp)
                )
            }
        },
        bottomBar = {
            BottomBar()
        }
    )
}
