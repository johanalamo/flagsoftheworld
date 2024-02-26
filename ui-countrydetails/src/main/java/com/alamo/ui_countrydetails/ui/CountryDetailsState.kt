package com.alamo.ui_countrydetails.ui

import com.alamo.country_domain.CountryDetails
import com.alamo.ui_countrydetails.util.Message
import java.util.LinkedList
import java.util.Queue

data class CountryDetailsState(
    val isLoading: Boolean = false,
    val data: CountryDetails? = null,
    val messages: Queue<Message> = LinkedList<Message>(),
)