package com.alamo.ui_countrylist.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
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
) {

    val snackbarHostState = SnackbarHostState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        textStyle = TextStyle.Default,
                        value = state.countryNameToSearch,
                        maxLines = 1,
                        singleLine = true,
                        onValueChange = {
                            events(CountryListEvents.UpdateCountryToSearch(it))
                        },
                        placeholder = { Text("Introduce a country")}
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                CountryList(state = state, events = events, snackbarHostState = snackbarHostState)
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
