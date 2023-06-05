package com.alamo.country_interactors

import kotlinx.coroutines.flow.Flow

interface UseCase {
    fun execute(): Flow<*>
}
