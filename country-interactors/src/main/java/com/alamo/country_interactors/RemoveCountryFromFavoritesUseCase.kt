package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.cache.CountryCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class RemoveCountryFromFavoritesUseCase(
    val countryCache: CountryCache
) : UseCase {
    override suspend fun execute(vararg parameters: String): Flow<DataState> = withContext(Dispatchers.IO) {
        flow {
            val countryCode = parameters[0]
            emit(DataState.Loading)
            if (countryCache.removeFromFavorites(countryCode)) {
                emit(DataState.Success<Nothing>())
            } else {
                emit(DataState.Error(DataState.ErrorType.UNKNOWN))
            }
        }
    }
}