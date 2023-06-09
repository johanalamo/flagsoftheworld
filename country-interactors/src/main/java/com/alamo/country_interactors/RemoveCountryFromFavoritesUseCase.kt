package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.cache.CountryCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveCountryFromFavoritesUseCase(
    val countryCache: CountryCache
) : UseCase {
    // TODO: it must be improved and exceptions should be tested
    override fun execute(vararg parameters: String): Flow<DataState> {
        return flow {
            val countryCode = parameters[0]
            emit(DataState.Loading)
            try {
                countryCache.removeFromFavorites(countryCode)
                emit(DataState.Success<Nothing>())
            } catch (e: Exception) {
                e.printStackTrace()
                emit(DataState.Error(999, "network error"))
            }
        }
    }
}