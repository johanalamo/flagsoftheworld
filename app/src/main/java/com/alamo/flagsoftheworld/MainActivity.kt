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
import com.alamo.country_interactors.GetCountriesUseCase
import com.alamo.ui_countrylist.composables.CountryList
import com.alamo.ui_countrylist.ui.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

//decidido.. a usar dos
//info about countries: https://restcountries.com/

//2. para las banderas (hay dos)
//esta que encontre
////country flags: https://countryflagsapi.com/
//
//y este que es la usada por restcountries.com para las flags
//https://flagcdn.com/
//tambien tienen el escudo, y referencia a google maps y openstree

//Think in how to use all of them above in the same project making sense

//add conection to database to set favorites or not,
//and get the whole lists of favorties from de database
//what would be the architecture design here ????? let's see
//
//- addAsFavorite(countryCode: String)
//- removeFromFavorite(contryCode: String)
//- isFavorite(countryCode: String): Boolean
//- getFavoriteCountries(): List<String>

/* TODO:

improve CountryList view
 - add favorite icon


add data base cache -room database
add add/remove favorite feature / shared preferences
add filter dialog (favorites)
add searching dialog

filter dialog example

+-------------------------------------+
| what to show
|    show all
|    show favorites
|show as
|    list
|    grid
|
|
|
+-------------------------------------+



learn

koin
gradle
readme.md
testing
testing jetpack compose

 */

class MainActivity : ComponentActivity() {

    // dependency injection video: https://www.youtube.com/watch?v=eH9UrAwKEcE
//    https://compilacionmovil.com/inyeccion-de-dependencias-en-android-con-koin-guia-basica/
//    https://devexperto.com/koin-inyeccion-dependencias/
//    https://www.youtube.com/watch?v=chCsNkjotfc 43 min
//    https://www.youtube.com/watch?v=GtrLY4vfZlw&t=1s
//    https://www.youtube.com/watch?v=EathumJlWh8

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
            )
        )
        testingCache()
        setContent {
            viewModel.onTriggerEvent(CountryListEvents.GetCountries)
            FlagsOfTheWorldTheme {
                // A surface container using the 'background' color from the theme
                CountryList(
                    state = viewModel.state.collectAsState().value,
                    events = viewModel::onTriggerEvent,
                )
            }
        }
    }

    private fun testingCache() {

        val driver: SqlDriver = AndroidSqliteDriver(CountryDatabase.Schema, this, "test.db")

        val countryDb = CountryDatabase(
            driver
        )
        val countryDatabase = CountryCacheImpl(
            countryDb
        )

        CoroutineScope(IO).launch {
            countryDatabase.removeAllFavorites()

            countryDatabase.addToFavorites("MNP")
            countryDatabase.addToFavorites("AND")
            var country = "VEN"
            println("alamo2 inserting $country: " + countryDatabase.addToFavorites(country))

            country = "COl"
            println("alamo2 inserting $country: " + countryDatabase.addToFavorites(country))

            country = "ARG"
            println("alamo2 inserting $country: " + countryDatabase.addToFavorites(country))
            println("alamo2 existe $country: " + countryDatabase.isFavorite(country))

            country = "ARg"
            println("alamo2 existe $country: " + countryDatabase.isFavorite(country))
            println("alamo2 removing $country: " + countryDatabase.removeFromFavorites(country))

            println("alamo2 removing $country: " + countryDatabase.removeFromFavorites(country))
            println("alamo2 existe $country: " + countryDatabase.isFavorite(country))

            val res2 = countryDatabase.getFavoriteCountries()

            println("alamo2 count: " + res2.size)
            println("alamo2 elements: $res2")
        }
    }
}
