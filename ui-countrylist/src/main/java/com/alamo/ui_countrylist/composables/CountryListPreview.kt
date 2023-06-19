package com.alamo.ui_countrylist.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alamo.country_domain.Country
import com.alamo.ui_countrylist.ui.CountryListEvents
import com.alamo.ui_countrylist.ui.CountryListState
import com.alamo.ui_countrylist.util.Message
import java.util.LinkedList

@Composable
@Preview
fun CountryListThreeElementsPreview() {
    val myLambda: (CountryListEvents) -> Unit = { s: CountryListEvents -> println(s) }
    CountryList(
        state = CountryListState(
            isLoading = false,
            list = listOf<Country>(
                Country(name = "Venezuela", codeISO3 = "VEN", capital = listOf("Caracas"), isFavorite = true),
                Country(name = "Argentina", codeISO3 = "ARG", capital = listOf("Buenos Aires")),
                Country(name = "Uruguay", codeISO3 = "URY", capital = listOf("Montevideo")),
            ),
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
                Country(name = "Venezuela", codeISO3 = "VEN", capital = listOf("Caracas")),
                Country(name = "Argentina", codeISO3 = "ARG", capital = listOf("Buenos Aires")),
                Country(name = "Uruguay", codeISO3 = "URY", capital = listOf("Montevideo")),
            ),
            messages = LinkedList<Message>(listOf(Message.NoInternetConnection)),
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
                Country(name = "Venezuela", codeISO3 = "VEN", capital = listOf("Caracas")),
                Country(name = "Argentina", codeISO3 = "ARG", capital = listOf("Buenos Aires")),
                Country(name = "Uruguay", codeISO3 = "URY", capital = listOf("Montevideo")),
            ),
        ),
        events = myLambda,
    )
}
