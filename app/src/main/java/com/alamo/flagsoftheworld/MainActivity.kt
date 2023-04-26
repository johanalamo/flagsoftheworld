package com.alamo.flagsoftheworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.alamo.flagsoftheworld.ui.theme.FlagsOfTheWorldTheme
import com.alamo.country_datasource.network.CountryService
import com.alamo.country_interactors.GetCountries
import com.alamo.ui_countrylist.ui.*

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


/* TODO:
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = CountryListViewModel(
            getCountries = GetCountries(
                CountryService.build()
            )
        )

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
}
