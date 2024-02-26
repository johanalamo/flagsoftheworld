package com.alamo.country_datasource.network

import com.alamo.country_datasource.network.Constants.BASE_URL
import com.alamo.country_datasource.network.model.CountryDetailsDto
import com.alamo.country_datasource.network.model.CountryDto
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryService {

    @GET("v3.1/all")
    suspend fun getAllCountries(): List<CountryDto>

    @GET("/v3.1/alpha/{code}")
    suspend fun getCountryDetails(
        @Path("code") countryCode: String
    ): List<CountryDetailsDto>

    companion object Factory {
        fun build(): CountryService {
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient()
                .newBuilder()
                .addInterceptor(
                    logging,
                )
                .build()
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
                .client(
                    client
                )
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
            return retrofit.create(CountryService::class.java)
        }
    }
}
