package com.alamo.ui_countrylist.ui

sealed class CountryListEvents {
    object GetCountries: CountryListEvents()
    object CloseErrorDialog: CountryListEvents()

    data class AddCountryToFavorites(val countryCode: String)

    data class RemoveCountryFromFavorites(val countryCode: String)
}
