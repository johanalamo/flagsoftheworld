package com.alamo.flagsoftheworld

import android.os.Bundle
import com.alamo.flagsoftheworld.Command.*
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alamo.flagsoftheworld.ui.theme.FlagsOfTheWorldTheme
import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.android.get
import retrofit2.Retrofit
import retrofit2.http.GET

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

@kotlinx.serialization.Serializable
data class CountryNameDto(
    @SerializedName("common")
    val common: String = ""
)

@kotlinx.serialization.Serializable
data class CountryDto(
    @SerializedName("name")
    val name: CountryNameDto? = null
//    @SerializedName("cioc")
//    val cioc: String? = null,
//    @SerializedName("name")
//    val name: String? = null
)

@kotlinx.serialization.Serializable
data class ShowDto(
    @SerializedName("name")
    val name: String? = null
//    @SerializedName("cioc")
//    val cioc: String? = null,
//    @SerializedName("name")
//    val name: String? = null
)


// Finished with retrofit!
interface CountryService {

    @GET("v3.1/all")
    suspend fun getAllCountries(): List<CountryDto>
}

interface MazeService {

    @GET("shows/1")
    suspend fun getShow(): ShowDto
}

@kotlinx.serialization.Serializable
data class Project( @SerializedName("my_name") val namemy: String,
                   val language: String)

class MainActivity : ComponentActivity() {

    // dependency injection video: https://www.youtube.com/watch?v=eH9UrAwKEcE
//    https://compilacionmovil.com/inyeccion-de-dependencias-en-android-con-koin-guia-basica/
//    https://devexperto.com/koin-inyeccion-dependencias/
//    https://www.youtube.com/watch?v=chCsNkjotfc 43 min
//    https://www.youtube.com/watch?v=GtrLY4vfZlw&t=1s
//    https://www.youtube.com/watch?v=EathumJlWh8


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = Project("kotlinx.serialization", "Koltin")
        println(Json.encodeToString(data))



        val student = get<Student>()
        student.beSmart()

        val student2 = get<Student>()
        student2.beSmart()

        val contentType = "application/json".toMediaType()

        var retrofit2: Retrofit = Retrofit
            .Builder()
            .baseUrl("https://api.tvmaze.com/")
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addConverterFactory(Json
                {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                }
                .asConverterFactory(contentType)
            )



            .build()

//        var countryService = retrofit.create(CountryService::class.java)
        var countryService = get<CountryService>()
        var mazeService = retrofit2.create(MazeService::class.java)


        var countryList: MutableState<List<CountryDto>> = mutableStateOf(listOf())

        CoroutineScope(IO).launch {
            delay(2000)
            countryList.value = countryService.getAllCountries()
//            println("avilan: " + countryList.value[10].name?.common)
        }

        CoroutineScope(IO).launch {
            CoroutineScope(Main).launch {
                val showName = mazeService.getShow()
                delay(2000)
                Toast.makeText(this@MainActivity, "showName from Maze: " + showName, Toast.LENGTH_SHORT).show()
            }
        }

        setContent {

            FlagsOfTheWorldTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn() {
                        itemsIndexed(items = countryList.value) { index, element ->
//                            println("avilan: " + (element.name?.common ?: "not available"))
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