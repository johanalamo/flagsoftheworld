package com.example.country_datasource.network

import com.example.country_datasource.network.Constants.BASE_URL
import com.example.country_datasource.network.model.CountryDto
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CountryService {

    @GET("v3.1/all")
    suspend fun getAllCountries(): List<CountryDto>

    companion object Factory {
        fun build(): CountryService {
            val retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
//                .addConverterFactory(Json
//                {
//                    ignoreUnknownKeys = true
//                    encodeDefaults = true
//                }
//                    .asConverterFactory("application/json".toMediaType())
//                )
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
            return retrofit.create(CountryService::class.java)
        }
    }
}
