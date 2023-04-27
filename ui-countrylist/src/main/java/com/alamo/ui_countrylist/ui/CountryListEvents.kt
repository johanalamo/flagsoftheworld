package com.alamo.ui_countrylist.ui

sealed class CountryListEvents {
    object GetCountries: CountryListEvents()
    object CloseErrorDialog: CountryListEvents()
}
