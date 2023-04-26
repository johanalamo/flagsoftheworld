package com.alamo.ui_countrylist.ui

import androidx.lifecycle.ViewModel
import com.alamo.core.domain.DataState
import com.alamo.country_domain.Country
import com.alamo.country_interactors.GetCountries
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CountryListViewModel(
    val getCountries: GetCountries,
) : ViewModel() {

    private val _state: MutableStateFlow<CountryListState> = MutableStateFlow(CountryListState())

    val state
        get() = _state.asStateFlow()

    fun onTriggerEvent(events: CountryListEvents) {
        when (events) {
            is CountryListEvents.GetCountries -> {
                getCountries()
            }
            CountryListEvents.CloseErrorDialog -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun getCountries() {
        CoroutineScope(Dispatchers.IO).launch {
            _state.update { it.copy(isLoading = true) }

            getCountries.execute().collect { dataState ->
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
                        _state.update { it.copy(error = Pair(100, "Unknown")) }
                        _state.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }
}