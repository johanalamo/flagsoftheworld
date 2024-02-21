package com.alamo.flagsoftheworld.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen {
    object CountryList : Screen() {
        val root = "countryList"
        val route = "countryList"
        fun createPath() = root
    }

    object CountryDetails : Screen() {
        object Fields {
            val countryCode = "countryCode"
        }

        val arguments = listOf(
            navArgument(Fields.countryCode) { type = NavType.StringType },
        )
        val root = "countryDetails"
        val route = "countryDetails/{${Fields.countryCode}}"
        fun createPath(countryCode: String) = "$root/$countryCode"
    }
}