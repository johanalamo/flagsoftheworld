package com.example.country_datasource.network.model

import com.example.country_domain.Country
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(
    @SerialName("name") val name: CountryNameDto? = null,
    @SerialName("region") val region: String? = null,
    @SerialName("subregion") val subregion: String? = null,
    @SerialName("flag") val flag: String? = null,
    @SerialName("population") val population: Long? = null,
)

fun CountryDto.toCountry(): Country =
    Country(
        name = name?.common,
        region = region,
        subregion = subregion,
        flag = flag,
        population = population,
    )