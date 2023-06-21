package com.alamo.ui_countrylist.util

sealed class Message {
    object InternetConnectionError: Message()
    object UnknownError: Message()

    data class AddedToFavorites(val countryCode: String) : Message()
    data class AddToFavoritesFailed(val countryCode: String): Message()

    data class RemovedFromFavorites(val countryCode: String) : Message()
    data class RemoveFromFavoritesFailed(val countryCode: String): Message()
}
