package com.alamo.flagsoftheworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.alamo.country_datasource.cache.CountryCacheImpl
import com.alamo.country_datasource.cache.CountryDatabase
import com.alamo.flagsoftheworld.ui.theme.FlagsOfTheWorldTheme
import com.alamo.country_datasource.network.CountryService
import com.alamo.country_interactors.AddCountryToFavoritesUseCase
import com.alamo.country_interactors.GetCountriesUseCase
import com.alamo.country_interactors.RemoveCountryFromFavoritesUseCase
import com.alamo.ui_countrylist.composables.CountryListScaffold
import com.alamo.ui_countrylist.ui.*

class MainActivity : ComponentActivity() {

    val driver: SqlDriver = AndroidSqliteDriver(CountryDatabase.Schema, this, "test.db")

    private val countryDb = CountryDatabase(
        driver
    )
    private val countryDatabase = CountryCacheImpl(
        countryDb
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = CountryListViewModel(
            getCountriesUseCase = GetCountriesUseCase(
                countryService = CountryService.build() ,
                countryCache = countryDatabase,
            ),
            addCountryToFavoritesUseCase = AddCountryToFavoritesUseCase(
                countryCache = countryDatabase,
            ),
            removeCountryFromFavoritesUseCase = RemoveCountryFromFavoritesUseCase(
                countryCache = countryDatabase,
            ),
        )
        setContent {
            viewModel.triggerEvent(CountryListEvents.GetCountries)
            FlagsOfTheWorldTheme {
                // A surface container using the 'background' color from the theme
                CountryListScaffold(
                    state = viewModel.state.collectAsState().value,
                    events = viewModel::triggerEvent,
                )
            }
        }
    }
}
