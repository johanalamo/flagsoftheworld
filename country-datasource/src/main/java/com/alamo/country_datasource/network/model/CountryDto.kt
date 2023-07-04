package com.alamo.country_datasource.network.model

import com.alamo.country_domain.Country
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(
    @SerialName("name") val name: CountryNameDto,
    @SerialName("region") val region: String? = null,
    @SerialName("subregion") val subregion: String? = null,
    @SerialName("flag") val flag: String? = null,
    @SerialName("capital") val capital: List<String>?,
    @SerializedName("cca3") val codeISO3: String,
    @SerialName("population") val population: Long? = null,
)

fun CountryDto.toCountry(): Country =
    Country(
        name = name.common,
        region = region,
        subregion = subregion,
        capital = capital,
        codeISO3 = codeISO3,
        flag = flag,
        population = population,
    )
