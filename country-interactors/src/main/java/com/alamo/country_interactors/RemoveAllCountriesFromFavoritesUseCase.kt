package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.cache.CountryCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class RemoveAllCountriesFromFavoritesUseCase(
    private val countryCache: CountryCache
) : UseCase {

    override suspend fun execute(vararg parameters: String): Flow<DataState> = withContext(Dispatchers.IO) {
        flow {
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