package com.alamo.country_interactors
import com.alamo.core.domain.DataState
import com.alamo.country_datasource.cache.CountryCache
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetFavoriteCountriesUseCaseTest {
    // Helper Values
    private val favoriteCountries = listOf<String>("VEN", "ARG", "URY")
    // End Helper values

    lateinit var SUT: GetFavoriteCountriesUseCase

    @Mock
    lateinit var countryCache: CountryCache

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        SUT = GetFavoriteCountriesUseCase(countryCache = countryCache)
    }

    @Test
    fun `executed() SHOULD emit success WHEN it returns the list from cache`() = runTest {
        // GIVEN
        whenever(countryCache.getFavoriteCountries()).thenReturn(favoriteCountries)

        // WHEN
        val emissions = mutableListOf<DataState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            SUT.execute().toList(emissions)
        }

        // THEN
        assertIs<DataState.Loading>(emissions[0])
        assertIs<DataState.Success<List<String>>>(emissions[1])
        assertEquals((emissions[1] as DataState.Success<*>).data, favoriteCountries)
    }


    @Test
    fun `executed() SHOULD emit error WHEN it something happens trying to recover the data`() = runTest {
        // GIVEN
        whenever(countryCache.getFavoriteCountries()).thenThrow()

        // WHEN
        val emissions = mutableListOf<DataState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            SUT.execute().toList(emissions)
        }

        // THEN
        assertIs<DataState.Loading>(emissions[0])
        assertIs<DataState.Error>(emissions[1])
    }
}
