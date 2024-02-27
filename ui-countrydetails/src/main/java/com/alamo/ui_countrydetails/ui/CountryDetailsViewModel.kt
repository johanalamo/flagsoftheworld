package com.alamo.ui_countrydetails.ui

import androidx.lifecycle.ViewModel
import com.alamo.core.domain.DataState
import com.alamo.country_domain.Country
import com.alamo.country_domain.CountryDetails
import com.alamo.country_interactors.UseCase
import com.alamo.ui_countrydetails.util.Message
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.LinkedList

class CountryDetailsViewModel(
    private val getCountryDetailsUseCase: UseCase,
    private val addCountryToFavoritesUseCase: UseCase,
    private val removeCountryFromFavoritesUseCase: UseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _state: MutableStateFlow<CountryDetailsState> = MutableStateFlow(CountryDetailsState())

    val state
        get() = _state.asStateFlow()

    fun triggerEvent(events: CountryDetailsEvents) {
        when (events) {
            is CountryDetailsEvents.GetCountryDetails -> getCountryDetails(events.countryCode)
            is CountryDetailsEvents.DismissTopMessage -> _state.update {
                // TODO: refactor, check if we can avoid to duplicate the list
                val newMessages: LinkedList<Message> = LinkedList<Message>(_state.value.messages.toList())
                if (newMessages.isNotEmpty()) {
                    newMessages.remove()
                }
                it.copy(messages = newMessages)
            }

            is CountryDetailsEvents.AddCountryToFavorites -> addCountryToFavorites(events.countryCode)
            is CountryDetailsEvents.RemoveCountryFromFavorites -> removeFromFavorites(events.countryCode)
        }
    }


    private fun getCountryDetails(countryCode: String) {
        // TODO: move it to a viewModel variable with MainDispatcher
        CoroutineScope(dispatcher).launch {
            _state.update { it.copy(isLoading = true, data = null) }
            var flow = getCountryDetailsUseCase.execute(countryCode)
            flow.collect { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is DataState.Success<*> -> {
                        @Suppress("UNCHECKED_CAST")
                        val data = dataState.data as CountryDetails?

                        if (data == null) {
                            _state.update { it.copy(data = null) }
                        } else {
                            _state.update { it.copy(data = data) }
                        }
                        _state.update { it.copy(isLoading = false) }
                    }

                    is DataState.Error -> {
                        // TODO: Implement message functionality
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
                                data = _state.value.data?.copy(isFavorite = true),
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
                            data = _state.value.data?.copy(isFavorite = false),
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
