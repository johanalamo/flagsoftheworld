package com.alamo.country_datasource.cache

interface CountryCache {
    suspend fun addToFavorites(countryCode: String): Boolean

    suspend fun removeFromFavorites(countryCode: String): Boolean

    suspend fun removeAllFavorites()

    suspend fun isFavorite(countryCode: String): Boolean

    suspend fun getFavoriteCountries(): List<String>
}
