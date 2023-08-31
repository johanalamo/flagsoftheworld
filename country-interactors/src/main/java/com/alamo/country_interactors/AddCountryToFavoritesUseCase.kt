package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.cache.CountryCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.lang.Exception

class AddCountryToFavoritesUseCase(
    private val countryCache: CountryCache
) : UseCase {
    override suspend fun execute(vararg parameters: String): Flow<DataState> = withContext(Dispatchers.IO) {
        flow {
            emit(DataState.Loading)
            val countryCode = parameters[0]
            if (countryCache.addToFavorites(countryCode = countryCode)) {
                emit(DataState.Success<Nothing>())
            } else {
                emit(DataState.Error(DataState.ErrorType.UNKNOWN))
            }
        }
    }
}