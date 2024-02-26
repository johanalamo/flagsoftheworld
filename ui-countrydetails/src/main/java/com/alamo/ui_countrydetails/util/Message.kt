package com.alamo.ui_countrydetails.util


// TODO: check if this class is necessary
sealed class Message {
    object InternetConnectionError: Message()

    object InternetConnectionSlow: Message()
    object UnknownError: Message()

    data class AddedToFavorites(val countryCode: String) : Message()
    data class AddToFavoritesFailed(val countryCode: String): Message()

    data class RemovedFromFavorites(val countryCode: String) : Message()
    data class RemoveFromFavoritesFailed(val countryCode: String): Message()
}
