package com.example.country_datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryNameDto(
    @SerialName("common") val common: String = ""
)