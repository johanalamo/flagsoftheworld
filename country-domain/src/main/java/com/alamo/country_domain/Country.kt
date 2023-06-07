package com.alamo.country_domain

data class Country (
    val name: String,
    val population: Long? = null,
    val region: String? = null,
    val codeISO3: String,
    val capital: List<String>?,
    val subregion: String? = null,
    val flag: String? = null,
    var isFavorite: Boolean = false,
)
