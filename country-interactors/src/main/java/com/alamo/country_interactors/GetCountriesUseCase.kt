package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.cache.CountryCache
import com.alamo.country_datasource.network.CountryService
import com.alamo.country_datasource.network.model.toCountry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetCountriesUseCase(
    private val countryService: CountryService,
    private val countryCache: CountryCache,
) : UseCase {
    // TODO: it must be improved and exceptions should be tested

    override fun execute(vararg parameters: String): Flow<DataState> {
        return flow {
            try {
                emit(DataState.Loading)
                val list = countryService.getAllCountries().map { it.toCountry() }
                val favorites = countryCache.getFavoriteCountries()
                list.forEach {
                    it.isFavorite = (favorites.contains(it.codeISO3))
                }
                emit(DataState.Success(
                    data = list
                ))
            } catch (e: Exception) {
                e.printStackTrace(System.err)
                emit(DataState.Error(code = 101, description = "No connection"))
            }
        }
    }
}
