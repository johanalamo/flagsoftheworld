package com.alamo.ui_countrylist.ui

import com.alamo.country_domain.Country

data class CountryListState(
    var isLoading: Boolean = false,
    var list: List<Country> = listOf<Country>(),
    val error: Pair<Int, String>? = null,
)