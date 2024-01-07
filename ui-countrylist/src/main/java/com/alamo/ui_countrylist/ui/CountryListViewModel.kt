package com.alamo.ui_countrylist.ui

import androidx.lifecycle.ViewModel
import com.alamo.core.domain.DataState
import com.alamo.country_domain.Country
import com.alamo.country_interactors.UseCase
import com.alamo.ui_countrylist.util.Message
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.LinkedList

class CountryListViewModel(
    private val getCountriesUseCase: UseCase,
    private val addCountryToFavoritesUseCase: UseCase,
    private val removeCountryFromFavoritesUseCase: UseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _state: MutableStateFlow<CountryListState> = MutableStateFlow(CountryListState())
    private var originalList: List<Country> = listOf<Country>()

    val state
        get() = _state.asStateFlow()

    fun triggerEvent(events: CountryListEvents) {
        when (events) {
            is CountryListEvents.GetCountries -> getCountries()
            is CountryListEvents.DismissTopMessage -> _state.update {
                // TODO: refactor, check if we can avoid to duplicate the list
                val newMessages: LinkedList<Message> = LinkedList<Message>(_state.value.messages.toList())
                if (newMessages.isNotEmpty()) {
                    newMessages.remove()
                }
                it.copy(messages = newMessages)
            }

            is CountryListEvents.AddUserCountryToFavorites -> addCountryToFavorites(events.countryCode)
            is CountryListEvents.RemoveUserCountryFromFavorites -> removeFromFavorites(events.countryCode)
            is CountryListEvents.UpdateCountryToSearch -> {
                filterCountries (events.countryToSearch)
                _state.update { it.copy(countryNameToSearch = events.countryToSearch) }
            }
        }
    }

    private fun filterCountries(countryToSearch: String) {
        _state.update {
            it.copy(
                list = originalList.filter {
                    it.name.contains(countryToSearch, true) || it.codeISO3.contains(countryToSearch, true)
                }.sortedBy {
                    it.name
                }.sortedBy {
                    it.region
                }
            )
        }
    }

    private fun getCountries() {
        // TODO: move it to a viewModel variable with MainDispatcher
        CoroutineScope(dispatcher).launch {
            _state.update { it.copy(isLoading = true) }
            var flow = getCountriesUseCase.execute()
            flow.collect { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is DataState.Success<*> -> {
                        @Suppress("UNCHECKED_CAST")
                        val data = dataState.data as List<Country>?
                        if ((data).isNullOrEmpty()) {
                            originalList = listOf()
                            _state.update { it.copy(list = listOf()) }
                        } else {
                            originalList = data
                            _state.update { it.copy(list = data) }
                        }
                        _state.update { it.copy(isLoading = false) }
                    }

                    is DataState.Error -> {
                        val newMessages: LinkedList<Message> = LinkedList<Message>(_state.value.messages.toList())
                        newMessages.add(
                            when (dataState.code) {
                                DataState.ErrorType.CONNECTION_ERROR -> Message.InternetConnectionError
                                DataState.ErrorType.CONNECTION_SLOW -> Message.InternetConnectionSlow
                                else -> Message.UnknownError
                            }
                        )
                        _state.update { it.copy(messages = newMessages, isLoading = false) }
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
                        _state.update {
                            it.copy(
                                list = _state.value.list.map { country ->
                                    if (country.codeISO3 == countryCode) country.isFavorite = true
                                    country
                                },
                                isLoading = false,
                                messages = LinkedList(_state.value.messages + Message.AddedToFavorites(countryCode)),
                            ) }
                    }
                    is DataState.Error -> {
                        var messages: LinkedList<Message> = LinkedList(_state.value.messages)
                        messages.add(Message.AddToFavoritesFailed(countryCode))
                        _state.update { it.copy(isLoading = false, messages = messages) }
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
                        _state.update { it.copy(
                            list = _state.value.list.map { country ->
                                if (country.codeISO3 == countryCode) country.isFavorite = false
                                country
                            },
                            isLoading = false,
                            messages = LinkedList(_state.value.messages + Message.RemovedFromFavorites(countryCode))
                        ) }
                    }
                    is DataState.Error -> {
                        var messages: LinkedList<Message> = LinkedList(_state.value.messages)
                        messages.add(Message.RemoveFromFavoritesFailed(countryCode))
                        _state.update { it.copy(isLoading = false, messages = messages) }
                    }
                }
            }
        }
    }
}
