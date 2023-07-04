package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.cache.CountryCache
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class AddCountryToFavoritesUseCaseTest {

    @Mock
    private lateinit var countryCache: CountryCache

    private lateinit var SUT: AddCountryToFavoritesUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        SUT = AddCountryToFavoritesUseCase(countryCache = countryCache)
    }

    @Test
    fun `execute() SHOULD return a success WHEN country is added as favorite`() = runTest {
        // GIVEN
        whenever(countryCache.addToFavorites(any())).thenReturn(true)

        // WHEN
        val emissions = mutableListOf<DataState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            SUT.execute("VEN").toList(emissions)
        }

        assertIs<DataState.Loading>(emissions[0])
        assertIs<DataState.Success<Nothing>>(emissions[1])
    }

    @Test
    fun `execute() SHOULD return a error WHEN an error occurs trying to add as favorite`() =
        runTest {
            // GIVEN
            whenever(countryCache.addToFavorites(any())).thenReturn(false)

            // WHEN
            val emissions = mutableListOf<DataState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                SUT.execute("VEN").toList(emissions)
            }

            assertIs<DataState.Loading>(emissions[0])
            assertIs<DataState.Error>(emissions[1])
        }
}