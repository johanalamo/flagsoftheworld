package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.network.CountryService
import com.alamo.country_datasource.network.model.toCountry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCountries(
    private val countryService: CountryService
) {

    fun execute(): Flow<DataState> {
        return flow {
            try {
                emit(DataState.Loading)
                val list = countryService.getAllCountries().map { it.toCountry() }
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