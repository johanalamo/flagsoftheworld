package com.alamo.ui_countrylist.ui

import com.alamo.country_domain.Country
import com.alamo.ui_countrylist.util.Message
import java.util.LinkedList
import java.util.Queue

data class CountryListState(
    val isLoading: Boolean = false,
    val list: List<Country> = listOf<Country>(),
    val messages: Queue<Message> = LinkedList<Message>(),

    // var favorite list
    // showing: all/favorite
    // showing: list/grid
    // isLoadingFromCache
    // isLoading -> isLoadingFromNetwork
    // isLoadingFavorites
    // isSavingFavorite
)