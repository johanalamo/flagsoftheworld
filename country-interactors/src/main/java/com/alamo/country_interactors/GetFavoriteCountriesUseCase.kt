package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.cache.CountryCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFavoriteCountriesUseCase(
    private val countryCache: CountryCache
){
    fun execute(): Flow<DataState> {
        return flow {
            emit(DataState.Loading)
            try {
                var favoriteList = countryCache.getFavoriteCountries()
                emit(DataState.Success(favoriteList))
            } catch (e: Exception) {
                emit(DataState.Error(DataState.ErrorType.UNKNOWN))
            }
        }
    }
}
