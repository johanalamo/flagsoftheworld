package com.alamo.ui_countrylist.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alamo.country_domain.Country
import com.alamo.jc_ui_components.GenericDialog
import com.alamo.jc_ui_components.Loader
import com.alamo.ui_countrylist.R
import com.alamo.ui_countrylist.ui.CountryListEvents
import com.alamo.ui_countrylist.ui.CountryListState
import com.alamo.ui_countrylist.util.Message

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryList(
    state: CountryListState,
    snackbarHostState: SnackbarHostState,
    events: (CountryListEvents) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier) {
            if (state.list.isNullOrEmpty()) {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "There is no element in the list",
                    )
                }
            } else {
                LazyColumn() {
                    val regions: Map<String, List<Country>> = state.list.groupBy {
                        it.region ?: "No region"
                    }
                    regions.forEach { region ->
                        stickyHeader {
                            Text(
                                text = region.key + " (" + region.value.size + ")",
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Left,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, bottom = 4.dp, top = 36.dp)
                                    .fillMaxWidth()
                                    .background(
                                        color = Color.Black,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    .padding(8.dp)
                            )
                        }
                        itemsIndexed(items = region.value.sortedBy { it.name }) { _, country ->
                            CountryCard(
                                name = country.name,
                                region = country.region,
                                subregion = country.subregion,
                                flag = country.flag,
                                codeISO3 = country.codeISO3,
                                isFavorite = country.isFavorite,
                                onClick = {
                                    println("avilan: click on country: " + country.name)
                                },
                                addToFavorites = {
                                    events(CountryListEvents.AddUserCountryToFavorites(country.codeISO3))
                                },
                                removeFromFavorites = {
                                    events(CountryListEvents.RemoveUserCountryFromFavorites(country.codeISO3))
                                }
                            )
                        }
                    }
                }
            }

            if (state.isLoading) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                ) {
                    Loader("Loading from the network")
                }
            }

            if (state.messages.isNotEmpty()) {
                when (state.messages.first()) {
                    is Message.InternetConnectionError -> {
                        GenericDialog(
                            title = stringResource(id = R.string.error),
                            description = stringResource(id = R.string.no_internet_connection),
                            onConfirm = {
                                events(CountryListEvents.DismissTopMessage)
                                events(CountryListEvents.GetCountries)
                            },
                            onConfirmText = stringResource(id = R.string.retry),
                            onDismiss = {
                                events(CountryListEvents.DismissTopMessage)
                            },
                            onDismissText = stringResource(id = R.string.cancel)
                        )
                    }
                    is Message.UnknownError -> {
                        ShowSnackbar(stringResource(id = R.string.unknown_error)) {
                            events(CountryListEvents.DismissTopMessage)
                        }
                    }
                    is Message.AddToFavoritesFailed -> {
                        val countryCode = (state.messages.first() as Message.AddToFavoritesFailed).countryCode
                        val countryName = state.list.first { it.codeISO3 == countryCode }.name
                        val message = stringResource(id = R.string.country_add_failed, countryName)
                        ShowSnackbar(message) { events(CountryListEvents.DismissTopMessage) }
                    }
                    is Message.AddedToFavorites -> {
                        val countryCode = (state.messages.first() as Message.AddedToFavorites).countryCode
                        val countryName = state.list.first { it.codeISO3 == countryCode }.name
                        val message = stringResource(id = R.string.country_added, countryName)
                        ShowTemporalSnackbar(snackbarHostState, message) { events(CountryListEvents.DismissTopMessage) }
                    }
                    is Message.RemoveFromFavoritesFailed -> {
                        val countryCode = (state.messages.first() as Message.RemoveFromFavoritesFailed).countryCode
                        val countryName = state.list.first { it.codeISO3 == countryCode }.name
                        val message = stringResource(id = R.string.country_remove_failed, countryName)
                        ShowSnackbar(message) { events(CountryListEvents.DismissTopMessage) }
                    }
                    is Message.RemovedFromFavorites -> {
                        val countryCode = (state.messages.first() as Message.RemovedFromFavorites).countryCode
                        val countryName = state.list.first { it.codeISO3 == countryCode }.name
                        val message = stringResource(id = R.string.country_removed, countryName)
                        ShowTemporalSnackbar(snackbarHostState, message) { events(CountryListEvents.DismissTopMessage) }
                    }
                    Message.InternetConnectionSlow -> {
                        ShowSnackbar(stringResource(id = R.string.internet_connection_slow)) {
                            events(CountryListEvents.DismissTopMessage)
                        }
                    }
                }
            }
        }
    }
}
