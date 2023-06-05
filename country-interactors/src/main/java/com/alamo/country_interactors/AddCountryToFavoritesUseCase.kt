package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.cache.CountryCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class AddCountryToFavoritesUseCase(
    private val countryCache: CountryCache
) {
    fun execute(countryCode: String): Flow<DataState> {
        return flow {
            emit(DataState.Loading)
            try {
                countryCache.addToFavorites(countryCode = countryCode)
                emit(DataState.Success<Nothing>())
            } catch (e: Exception) {
                e.printStackTrace()
                emit(DataState.Error(999, "network error"))
            }
        }
    }
}