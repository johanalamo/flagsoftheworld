package com.alamo.ui_countrylist.composables

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alamo.country_domain.Country
import com.alamo.ui_countrylist.ui.CountryListEvents
import com.alamo.ui_countrylist.ui.CountryListState
import com.alamo.ui_countrylist.util.Message
import java.util.LinkedList

val listOfCountries = listOf<Country>(
    Country(name = "Venezuela", codeISO3 = "VEN", capital = listOf("Caracas"), isFavorite = true),
    Country(name = "Argentina", codeISO3 = "ARG", capital = listOf("Buenos Aires")),
    Country(name = "Uruguay", codeISO3 = "URY", capital = listOf("Montevideo")),
)
@Composable
@Preview
fun CountryListThreeElementsPreview() {
    val myLambda: (CountryListEvents) -> Unit = { s: CountryListEvents -> println(s) }
    CountryList(
        state = CountryListState(
            isLoading = false,
            list = listOfCountries,
        ),
        events = myLambda,
        snackbarHostState = SnackbarHostState(),
    )
}

@Composable
@Preview
fun CountryListLoadingPreview() {
    val myLambda: (CountryListEvents) -> Unit = { s: CountryListEvents -> println(s) }
    CountryList(
        state = CountryListState(
            isLoading = true,
            list = listOfCountries,
        ),
        events = myLambda,
        snackbarHostState = SnackbarHostState(),
    )
}

@Composable
@Preview
fun CountryListInternetConnectionErrorPreview() {
    val myLambda: (CountryListEvents) -> Unit = { s: CountryListEvents -> println(s) }
    CountryList(
        state = CountryListState(
            isLoading = false,
            list = listOfCountries,
            messages = LinkedList<Message>(listOf(Message.InternetConnectionError)),
        ),
        events = myLambda,
        snackbarHostState = SnackbarHostState(),
    )
}

@Composable
@Preview
fun CountryListInternetConnectionSlowPreview() {
    val myLambda: (CountryListEvents) -> Unit = { s: CountryListEvents -> println(s) }
    CountryList(
        state = CountryListState(
            isLoading = false,
            list = listOfCountries,
            messages = LinkedList<Message>(listOf(Message.InternetConnectionSlow)),
        ),
        events = myLambda,
        snackbarHostState = SnackbarHostState(),
    )
}

@Composable
@Preview
fun CountryListUnknownErrorPreview() {
    val myLambda: (CountryListEvents) -> Unit = { s: CountryListEvents -> println(s) }
    CountryList(
        state = CountryListState(
            isLoading = false,
            list = listOfCountries,
            messages = LinkedList<Message>(listOf(Message.UnknownError)),
        ),
        events = myLambda,
        snackbarHostState = SnackbarHostState(),
    )
}

@Composable
@Preview
fun CountryListRemoveFromFavoritesFailedPreview() {
    val myLambda: (CountryListEvents) -> Unit = { s: CountryListEvents -> println(s) }
    CountryList(
        state = CountryListState(
            isLoading = false,
            list = listOfCountries,
            messages = LinkedList<Message>(listOf(Message.RemoveFromFavoritesFailed("VEN"))),
        ),
        events = myLambda,
        snackbarHostState = SnackbarHostState(),
    )
}

@Composable
@Preview
fun CountryListAddToFavoritesFailedPreview() {
    val myLambda: (CountryListEvents) -> Unit = { s: CountryListEvents -> println(s) }
    CountryList(
        state = CountryListState(
            isLoading = false,
            list = listOfCountries,
            messages = LinkedList<Message>(listOf(Message.AddToFavoritesFailed("VEN"))),
        ),
        events = myLambda,
        snackbarHostState = SnackbarHostState(),
    )
}


@Composable
@Preview
fun CountryListAddedToFavoritesPreview() {
    val myLambda: (CountryListEvents) -> Unit = { s: CountryListEvents -> println(s) }
    CountryList(
        state = CountryListState(
            isLoading = false,
            list = listOfCountries,
            messages = LinkedList<Message>(listOf(Message.AddedToFavorites("VEN"))),
        ),
        events = myLambda,
        snackbarHostState = SnackbarHostState(),
    )
}

@Composable
@Preview
fun CountryRemovedFromFavoritesPreview() {
    val myLambda: (CountryListEvents) -> Unit = { s: CountryListEvents -> println(s) }
    CountryList(
        state = CountryListState(
            isLoading = false,
            list = listOfCountries,
            messages = LinkedList<Message>(listOf(Message.RemovedFromFavorites("VEN"))),
        ),
        events = myLambda,
        snackbarHostState = SnackbarHostState(),
    )
}
