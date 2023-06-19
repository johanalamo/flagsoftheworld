package com.alamo.ui_countrylist.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
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
    events: (CountryListEvents) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier) {
            if (state.error.isNotEmpty()) {
                when (state.error.first()) {
                    // TODO: add here toasts, snackbars and dialogs
                    is Message.NoInternetConnection -> {
                        GenericDialog(
                            title = "Error ",
                            description = "No internet connection",
                            onConfirm = {
                                events(CountryListEvents.DismissTopMessage)
                                events(CountryListEvents.GetCountries)
                            },
                            onConfirmText = "Retry",
                            onDismiss = {
                                events(CountryListEvents.DismissTopMessage)
                            },
                            onDismissText = "Cancel"
                        )
                    }

                    is Message.UnknownError -> {
                        GenericDialog(
                            title = "Error",
                            description = "UNKNOWN ERROR",
                            onConfirm = {
                                events(CountryListEvents.DismissTopMessage)
                                events(CountryListEvents.GetCountries)
                            },
                            onConfirmText = "Retry",
                            onDismiss = {
                                events(CountryListEvents.DismissTopMessage)
                            },
                            onDismissText = "Cancel"
                        )
                    }

                    is Message.AddToFavoritesFailed -> {
                        // TODO: replace with a snackbar
                        val countryCode = (state.error.first() as Message.AddToFavoritesFailed).countryCode
                        val countryName = state.list.first { it.codeISO3 == countryCode }.name
                        val message = stringResource(id = R. string.country_add_failed, countryName)
                        println(message)
                        events(CountryListEvents.DismissTopMessage)
                    }

                    is Message.AddedToFavorites -> {
                        // TODO: replace with a snackbar
                        val countryCode = (state.error.first() as Message.AddedToFavorites).countryCode
                        val countryName = state.list.first { it.codeISO3 == countryCode }.name
                        val message = stringResource(id = R. string.country_added, countryName)
                        println(message)
                        events(CountryListEvents.DismissTopMessage)
                    }

                    is Message.RemoveFromFavoritesFailed -> {
                        // TODO: replace with a snackbar
                        val countryCode = (state.error.first() as Message.RemoveFromFavoritesFailed).countryCode
                        val countryName = state.list.first { it.codeISO3 == countryCode }.name
                        val message = stringResource(id = R. string.country_remove_failed, countryName)
                        println(message)
                        events(CountryListEvents.DismissTopMessage)
                    }

                    is Message.RemovedFromFavorites -> {
                        // TODO: replace with a snackbar
                        val countryCode = (state.error.first() as Message.RemovedFromFavorites).countryCode
                        val countryName = state.list.first { it.codeISO3 == countryCode }.name
                        val message = stringResource(id = R. string.country_removed, countryName)
                        println(message)
                        events(CountryListEvents.DismissTopMessage)
                    }
                }
            }

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
        }
    }
}