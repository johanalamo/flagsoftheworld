package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.cache.CountryCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveAllCountriesFromFavoritesUseCase(
    private val countryCache: CountryCache
) {

    fun execute(): Flow<DataState> {
        return flow {
            emit(DataState.Loading)
            try {
                countryCache.removeAllFavorites()
                emit(DataState.Success<Nothing>())
            } catch (e: Exception) {
                e.printStackTrace()
                emit(DataState.Error(DataState.ErrorType.UNKNOWN))
            }
        }
    }
}