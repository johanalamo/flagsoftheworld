package com.alamo.country_datasource.network.model

import com.alamo.country_domain.CountryDetails
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryDetailsDto(
    @SerialName("name") val name: CountryNameDto,
    @SerialName("region") val region: String? = null,
    @SerialName("subregion") val subregion: String? = null,
    @SerialName("flag") val flag: String? = null,
    @SerialName("capital") val capital: List<String>?,
    @SerializedName("cca3") val codeISO3: String,
    @SerialName("population") val population: Long? = null,
    @SerialName("area") val area: Long? = null,
    @SerialName("borders") val borders: List<String>?,
    @SerialName("latlng") val latlng: List<Double>?,
    @SerialName("fifa") val fifa: String? = null,
    @SerialName("timezones") val timezones: List<String>?,
    @SerializedName("flags") val flagsURLDto: FlagsURLDto? = null,
    @SerializedName("coatOfArms") val coatOfArms: CoatOfArmsDto? = null,
)

fun CountryDetailsDto.toCountryDetails(): CountryDetails =
    CountryDetails(
        name = name.common,
        officialName = name.official,
        region = region,
        subregion = subregion,
        capital = capital,
        codeISO3 = codeISO3,
        flag = flag,
        population = population,
        area = area,
        borders = borders,
        latlng = latlng,
        fifa = fifa,
        timezones = timezones,
        flagUrl = flagsURLDto?.png,
        flagDescription = flagsURLDto?.alt,
        coatOfArmsUrl = coatOfArms?.png,
    )
