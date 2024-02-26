package com.alamo.country_datasource.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoatOfArmsDto(
    @SerialName("png") val png: String = "",
    @SerialName("svg") val svg: String = "",
)