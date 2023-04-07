package com.alamo.flagsoftheworld

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

val testingKoinModules = module {
    // Defines a Singleton of Course
    single {
        Course()
    }

    // Defines a Factory (creates new instance every time it is needed)
    factory {
        Friend()
    }

    // Defines a Factory (creates new instance every time it is needed)
    factory {
        Student(get(), get())
    }

}

val appModules = module {
    single {
        Retrofit
            .Builder()
            .baseUrl("https://restcountries.com/")
//            .baseUrl("https://api.tvmaze.com/")
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addConverterFactory(Json
            {
                ignoreUnknownKeys = true
                encodeDefaults = true
            }
                .asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    single {
        val retrofit = get<Retrofit>()
        retrofit.create(CountryService::class.java)
    }
}