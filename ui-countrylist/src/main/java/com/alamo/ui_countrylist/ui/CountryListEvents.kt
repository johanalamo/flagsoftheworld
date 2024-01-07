package com.alamo.ui_countrylist.ui

sealed class CountryListEvents {
    object GetCountries: CountryListEvents()
    object DismissTopMessage: CountryListEvents()

    data class AddUserCountryToFavorites(val countryCode: String): CountryListEvents()

    data class RemoveUserCountryFromFavorites(val countryCode: String): CountryListEvents()

    data class UpdateCountryToSearch(val countryToSearch: String): CountryListEvents()
}
