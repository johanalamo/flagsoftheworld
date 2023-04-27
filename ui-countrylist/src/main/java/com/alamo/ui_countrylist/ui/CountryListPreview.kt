package com.alamo.ui_countrylist.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alamo.country_domain.Country

@Composable
@Preview
fun CountryListThreeElementsPreview() {
    val myLambda: (CountryListEvents) -> Unit = { s: CountryListEvents -> println(s) }
    CountryList(
        state = CountryListState(
            isLoading = false,
            list = listOf<Country>(
                Country(name = "Venezuela"),
                Country(name = "Argentina"),
                Country(name = "Uruguay"),
            ),
            error = null,
        ),
        events = myLambda,
    )
}

@Composable
@Preview
fun CountryListErrorPreview() {
    val myLambda: (CountryListEvents) -> Unit = { s: CountryListEvents -> println(s) }
    CountryList(
        state = CountryListState(
            isLoading = false,
            list = listOf<Country>(
                Country(name = "Venezuela"),
                Country(name = "Argentina"),
                Country(name = "Uruguay"),
            ),
            error = Pair(103, "Testing an error"),
        ),
        events = myLambda,
    )
}

@Composable
@Preview
fun CountryListLoadingPreview() {
    val myLambda: (CountryListEvents) -> Unit = { s: CountryListEvents -> println(s) }
    CountryList(
        state = CountryListState(
            isLoading = true,
            list = listOf<Country>(
                Country(name = "Venezuela"),
                Country(name = "Argentina"),
                Country(name = "Uruguay"),
            ),
            error = null,
        ),
        events = myLambda,
    )
}
