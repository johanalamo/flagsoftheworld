package com.alamo.country_domain

data class CountryDetails (
    val name: String,
    val officialName: String,
    val population: Long? = null,
    val area: Long? = null,
    val region: String? = null,
    val codeISO3: String,
    val capital: List<String>?,
    val subregion: String? = null,
    val flag: String? = null,
    var isFavorite: Boolean = false,
    val borders: List<String>?,
    val latlng: List<Double>?,
    val fifa: String? = null,
    val timezones: List<String>?,
    val flagUrl: String? = null,
    val flagDescription: String? = null,
    val coatOfArmsUrl: String? = null,
)
