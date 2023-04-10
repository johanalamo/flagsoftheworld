package com.alamo.flagsoftheworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alamo.flagsoftheworld.ui.theme.FlagsOfTheWorldTheme
import com.example.country_datasource.network.CountryService
import com.example.country_datasource.network.model.CountryDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
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

        var countryList: MutableState<List<CountryDto>> = mutableStateOf(listOf())

        CoroutineScope(IO).launch {
            delay(2000)
            countryList.value = countryService.getAllCountries()
        }

        setContent {

            FlagsOfTheWorldTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn() {
                        itemsIndexed(items = countryList.value) { _, element ->
                            Text(text = element.name?.common ?: "not available")
                        }
                    }
                }
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