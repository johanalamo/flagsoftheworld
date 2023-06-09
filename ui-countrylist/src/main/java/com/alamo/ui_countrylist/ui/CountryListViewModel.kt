package com.alamo.ui_countrylist.ui

import androidx.lifecycle.ViewModel
import com.alamo.core.domain.DataState
import com.alamo.country_domain.Country
import com.alamo.country_interactors.AddCountryToFavoritesUseCase
import com.alamo.country_interactors.UseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update

class CountryListViewModel(
    private val getCountriesUseCase: UseCase,
    private val addCountryToFavoritesUseCase: UseCase,
    private val removeCountryFromFavoritesUseCase: UseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _state: MutableStateFlow<CountryListState> = MutableStateFlow(CountryListState())

    val state
        get() = _state.asStateFlow()

    fun onTriggerEvent(events: CountryListEvents) {
        when (events) {
            is CountryListEvents.GetCountries -> getCountries()
            is CountryListEvents.CloseErrorDialog -> _state.update { it.copy(error = null) }
            is CountryListEvents.AddCountryToFavorites -> addCountryToFavorites(events.countryCode)
            is CountryListEvents.RemoveCountryFromFavorites -> removeFromFavorites(events.countryCode)
        }
    }

    private fun getCountries() {
        CoroutineScope(dispatcher).launch {
            _state.update { it.copy(isLoading = true) }
            var flow = getCountriesUseCase.execute()
            flow.collect { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is DataState.Success<*> -> {
                        val data = dataState.data as List<Country>?
                        if ((data).isNullOrEmpty()) {
                            _state.update { it.copy(list = listOf()) }
                        } else {
                            _state.update { it.copy(list =  data) }
                        }
                        _state.update { it.copy(isLoading = false) }
                    }
                    is DataState.Error -> {
                        _state.update {
                            it.copy(
                                error = Pair(dataState.code, dataState.description),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun addCountryToFavorites(countryCode: String) {
        CoroutineScope(dispatcher).launch {
            var flow = addCountryToFavoritesUseCase.execute(countryCode)
            flow.collect { dataState ->
                when (dataState) {
                    DataState.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is DataState.Success<*> -> {
                        var mutableList = _state.value.list.map { it }
                        val pos = mutableList.indexOfFirst { it.codeISO3 == countryCode }
                        mutableList[pos].isFavorite = true
                        _state.update { it.copy(list = mutableList) }
                        _state.update { it.copy(isLoading = false) }
                    }
                    is DataState.Error -> {
                        _state.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }

    private fun removeFromFavorites(countryCode: String) {
        CoroutineScope(dispatcher).launch {
            var flow = removeCountryFromFavoritesUseCase.execute(countryCode)
            flow.collect { dataState ->
                when (dataState) {
                    DataState.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is DataState.Success<*> -> {
                        var mutableList = _state.value.list.map { it }
                        val pos = mutableList.indexOfFirst { it.codeISO3 == countryCode }
                        mutableList[pos].isFavorite = false
                        _state.update { it.copy(list = mutableList) }
                        _state.update { it.copy(isLoading = false) }
                    }
                    is DataState.Error -> {
                        _state.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }
}
