package com.alamo.ui_countrylist.ui

import com.alamo.country_domain.Country

// todo: in construction, still unused

data class CountryListState(
    val showingList: List<Country> = listOf(),
    val showingEmptyList: Boolean = false,
    val showingError: Boolean = false,

) {


}