package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.network.CountryService
import com.alamo.country_datasource.network.model.toCountry
import com.alamo.country_domain.Country
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCountries(
    private val countryService: CountryService
) {

    fun execute(): Flow<DataState<List<Country>>> {
        return flow {
            try {
                emit(DataState.Loading(true))
                delay(2000)
                val list = countryService.getAllCountries().map { it.toCountry() }
                emit(DataState.Success(
                    data = list
                ))
            } catch (e: Exception) {
                emit(DataState.Error(null))
            }
        }
    }
}