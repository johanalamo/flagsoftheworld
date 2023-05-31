package com.alamo.country_datasource.cache

import com.alamo.countrydatasource.cache.CountryDbQueries
import kotlin.math.truncate

class CountryCacheImpl(
    countryDatabase: CountryDatabase,
): CountryCache {

    private var queries: CountryDbQueries = countryDatabase.countryDbQueries
    override suspend fun addToFavorites(countryCode: String): Boolean {
        return try {
            queries.addFavorite(codeIso = countryCode.uppercase())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun removeFromFavorites(countryCode: String): Boolean {
        return try {
            queries.removeFavorite(codeIso = countryCode.uppercase())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            true
        }
    }

    override suspend fun removeAllFavorites() {
        queries.removeAllFavorites()
    }

    override suspend fun isFavorite(countryCode: String): Boolean {
        return getFavoriteCountries().contains(countryCode.uppercase())
    }

    override suspend fun getFavoriteCountries(): List<String> {
        return queries.getFavorites().executeAsList()
    }
}
