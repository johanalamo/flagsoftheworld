package com.alamo.ui_countrylist.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alamo.jc_ui_components.GenericDialog
import com.alamo.jc_ui_components.Loader
import com.alamo.ui_countrylist.R
import com.alamo.ui_countrylist.ui.CountryListEvents
import com.alamo.ui_countrylist.ui.CountryListState
import com.alamo.ui_countrylist.util.Message

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
                Text(text = "There is no element in the list")
            } else {
                LazyColumn() {
                    state.list.let {
                        itemsIndexed(items = it) { _, country ->
                            CountryCard(
                                name = country.name,
                                region = country.region,
                                subregion = country.subregion,
                                flag = country.flag,
                                codeISO3 = country.codeISO3,
                                population = country.population,
                                capital = country.capital,
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
