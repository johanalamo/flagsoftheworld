package com.alamo.ui_countrylist.ui

sealed class CountryListUserEvents {
    object GetCountries: CountryListUserEvents()
    object CloseErrorDialog: CountryListUserEvents()

    data class AddUserCountryToFavorites(val countryCode: String): CountryListUserEvents()

    data class RemoveUserCountryFromFavorites(val countryCode: String): CountryListUserEvents()
}
