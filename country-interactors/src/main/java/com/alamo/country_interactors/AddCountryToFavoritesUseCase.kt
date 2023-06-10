package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.cache.CountryCache
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class AddCountryToFavoritesUseCase(
    private val countryCache: CountryCache
) : UseCase {
    // TODO: it must be improved and exceptions should be tested
    override fun execute(vararg parameters: String): Flow<DataState> {
        return flow {
            emit(DataState.Loading)
            val countryCode = parameters[0]
            if (countryCache.addToFavorites(countryCode = countryCode)) {
                emit(DataState.Success<Nothing>())
            } else {
                emit(DataState.Error(999, "network error"))
            }
        }
    }
}