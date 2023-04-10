package com.example.country_datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(
    @SerialName("name")
    val name: CountryNameDto? = null
//    @SerializedName("cioc")
//    val cioc: String? = null,
//    @SerializedName("name")
//    val name: String? = null
)