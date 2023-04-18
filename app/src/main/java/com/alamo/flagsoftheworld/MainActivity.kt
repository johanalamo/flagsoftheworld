package com.alamo.flagsoftheworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alamo.core.domain.DataState
import com.alamo.flagsoftheworld.ui.theme.FlagsOfTheWorldTheme
import com.alamo.country_datasource.network.CountryService
import com.alamo.country_datasource.network.model.toCountry
import com.alamo.country_domain.Country
import com.alamo.country_interactors.GetCountries
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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

class MainActivity : ComponentActivity() {

    // dependency injection video: https://www.youtube.com/watch?v=eH9UrAwKEcE
//    https://compilacionmovil.com/inyeccion-de-dependencias-en-android-con-koin-guia-basica/
//    https://devexperto.com/koin-inyeccion-dependencias/
//    https://www.youtube.com/watch?v=chCsNkjotfc 43 min
//    https://www.youtube.com/watch?v=GtrLY4vfZlw&t=1s
//    https://www.youtube.com/watch?v=EathumJlWh8


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val countryService = CountryService.build()
        var countryList: MutableState<List<Country>?> = mutableStateOf(listOf())
        CoroutineScope(IO).launch {
            delay(2000)
//            countryList.value = countryService.getAllCountries().map { it.toCountry() }
        }

        var getCountries: GetCountries = GetCountries(countryService)
        println("avilan before execute")
        getCountries.execute().onEach { dataState ->  
            when (dataState) {
                is DataState.Loading -> {
                    println("avilan -> is Loading: " + dataState.isLoading)
                }
                is DataState.Error -> {
                    println("avilan -> is DataError: " + dataState.error)
                }
                is DataState.Success -> {
                    val list = dataState.data
                    countryList.value = dataState.data
                    println("avilan success: ")
                    list?.forEach {
                        println("avilan pais: " + it.name)
                    }
                }
            }
        }.launchIn(GlobalScope)

        setContent {

            FlagsOfTheWorldTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn() {
                        countryList.value?.let {
                            itemsIndexed(items = it) { _, element ->
                                CountryCard(
                                    name = element.name,
                                    region = element.region,
                                    subregion = element.subregion,
                                    flag = element.flag,
                                    population = element.population,
                                ) {

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryCard(
    name: String?,
    region: String?,
    subregion: String?,
    flag: String?,
    population: Long?,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, color = Color.Black),
        onClick = {
            onClick()
        },
    ) {
        Column {
            Row {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = flag ?: "",
                )
                Text(text = name ?: "undefinded")
            }
            Text(text = region ?: "no_region")
            Text(text = subregion ?: "no_subregion")
            Row {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = "Population:",
                )
                Text(text = population.toString())
            }
        }
    }
}

@Composable
fun MyButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(onClick = {
        onClick()
    }) {
        Text(text = text)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FlagsOfTheWorldTheme {
        Greeting("Android")
    }
}