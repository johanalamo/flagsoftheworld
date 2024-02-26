package com.alamo.ui_countrydetails.ui

sealed class CountryDetailsEvents {
    object DismissTopMessage: CountryDetailsEvents()
    data class GetCountryDetails(val countryCode: String): CountryDetailsEvents()
    data class AddCountryToFavorites(val countryCode: String): CountryDetailsEvents()
    data class RemoveCountryFromFavorites(val countryCode: String): CountryDetailsEvents()
}
