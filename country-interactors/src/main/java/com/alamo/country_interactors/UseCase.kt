package com.alamo.country_interactors

import kotlinx.coroutines.flow.Flow

interface UseCase {
    suspend fun execute(vararg parameters: String): Flow<*>
}
