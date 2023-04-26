package com.alamo.ui_countrylist.ui

// todo: in construction, still unused

sealed class CountryListEvents {

    object GetCountries: CountryListEvents()
    object CloseErrorDialog: CountryListEvents()

}