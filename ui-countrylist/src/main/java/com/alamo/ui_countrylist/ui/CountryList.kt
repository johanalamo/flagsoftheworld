package com.alamo.ui_countrylist.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alamo.country_domain.Country
import com.alamo.jc_ui_components.GenericDialog
import com.alamo.jc_ui_components.Loader
import com.alamo.ui_countrylist.composables.CountryCard

@Composable
fun CountryList(
    state: CountryListState,
    events: (CountryListEvents) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        if (state.error != null) {
            GenericDialog(
                title = "Error " + state.error?.first,
                description = "Error trying to get the contry list from the network: "
                        + state.error?.second,
                onConfirm = {
                    events(CountryListEvents.CloseErrorDialog)
                    events(CountryListEvents.GetCountries)
                },
                onConfirmText = "Retry",
                onDismiss = {
                    events(CountryListEvents.CloseErrorDialog)
                },
                onDismissText = "Cancel"
            )
        }

        if (state.list.isNullOrEmpty()) {
            Text(text = "There is no element in the list")
        } else {
            LazyColumn() {
                state.list.let {
                    itemsIndexed(items = it) { _, element ->
                        val country = element as Country
                        CountryCard(
                            name = country.name,
                            region = country.region,
                            subregion = country.subregion,
                            flag = country.flag,
                            population = country.population,
                        ) {

                        }
                    }
                }
            }
        }

        if (state.isLoading) {
            Loader("Loading from the network")
        }
    }
}