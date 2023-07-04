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
class RemoveAllCountriesFromFavoritesUseCaseTest {

    @Mock
    private lateinit var countryCache: CountryCache

    private lateinit var SUT: RemoveAllCountriesFromFavoritesUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        SUT = RemoveAllCountriesFromFavoritesUseCase(countryCache = countryCache)
    }

    @Test
    fun `execute() SHOULD return a success WHEN all countries are removed from favorites`() = runTest {
        // GIVEN
        whenever(countryCache.removeAllFavorites()).thenReturn(Unit)

        // WHEN
        val emissions = mutableListOf<DataState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            SUT.execute().toList(emissions)
        }

        assertIs<DataState.Loading>(emissions[0])
        assertIs<DataState.Success<Nothing>>(emissions[1])
    }

    @Test
    fun `execute() SHOULD return a error WHEN an error occurs trying to remove all countries from favorites`() =
        runTest {
            // GIVEN
            whenever(countryCache.removeAllFavorites()).thenThrow()

            // WHEN
            val emissions = mutableListOf<DataState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                SUT.execute().toList(emissions)
            }

            assertIs<DataState.Loading>(emissions[0])
            assertIs<DataState.Error>(emissions[1])
        }
}