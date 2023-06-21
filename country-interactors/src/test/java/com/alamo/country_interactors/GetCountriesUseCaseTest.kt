package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.cache.CountryCache
import com.alamo.country_datasource.network.CountryService
import com.alamo.country_datasource.network.model.CountryDto
import com.alamo.country_datasource.network.model.CountryNameDto
import com.alamo.country_domain.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetCountriesUseCaseTest {

    // Helper values
    private val countryList = listOf<CountryDto>(
        CountryDto(name = CountryNameDto(common = "Venezuela"), codeISO3 = "VEN", capital = listOf("Caracas")),
        CountryDto(name = CountryNameDto(common = "Argentina"), codeISO3 = "ARG", capital = listOf("Buenos Aires")),
        CountryDto(name = CountryNameDto(common = "Uruguay"), codeISO3 = "URY", capital = listOf("Montevideo")),
    )
    private val httpException404 = HttpException(
        Response.error<Any>(
            404, ResponseBody.create(
                MediaType.parse("plain/text"), ""
            )
        )
    )
    private val favoriteCountries = listOf<String>("VEN","ARG")

    // End Helper values

    lateinit var SUT: GetCountriesUseCase

    @Mock
    lateinit var countryService: CountryService

    @Mock
    lateinit var countryCache: CountryCache

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        SUT = GetCountriesUseCase(countryService, countryCache)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun  `execute() SHOULD emit a country list WHEN it is connected`() = runTest {
        // GIVEN
        setSuccess()

        // WHEN
        val emissions = mutableListOf<DataState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            SUT.execute().toList(emissions)
        }

        // THEN
        assertIs<DataState.Loading>(emissions[0])
        assertIs<DataState.Success<List<CountryDto>>>(emissions[1])
    }

    @Test
    fun  `execute() SHOULD set favorites WHEN there are favorites in the cache`() = runTest {
        // GIVEN
        setSuccess()

        // WHEN
        val emissions = mutableListOf<DataState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            SUT.execute().toList(emissions)
        }

        // THEN
        assertIs<DataState.Loading>(emissions[0])
        assertIs<DataState.Success<List<CountryDto>>>(emissions[1])

        @Suppress("UNCHECKED_CAST")
        val favoritesFromCache = (emissions[1] as DataState.Success<List<Country>>).data!!.filter {
            it.isFavorite
        }.map {
            it.codeISO3
        }

        assertEquals(favoriteCountries.size, favoritesFromCache.size)
        favoriteCountries.forEach {
            assertTrue(favoritesFromCache.contains(it))
        }
    }

    @Test
    fun  `execute() SHOULD emit an connection_error WHEN a network connection error happens`() = runTest {
        // GIVEN
        whenever(countryService.getAllCountries()).doAnswer { throw UnknownHostException() }

        // WHEN
        val emissions = mutableListOf<DataState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            SUT.execute().toList(emissions)
        }

        // THEN
        assertIs<DataState.Loading>(emissions[0])
        assertIs<DataState.Error>(emissions[1])
        assertEquals(DataState.ErrorType.CONNECTION_ERROR, (emissions[1] as DataState.Error).code)
    }

    @Test
    fun  `execute() SHOULD emit an connection_error WHEN a malformed data is returned`() = runTest {
        // GIVEN
        whenever(countryService.getAllCountries()).doAnswer { throw NullPointerException() }

        // WHEN
        val emissions = mutableListOf<DataState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            SUT.execute().toList(emissions)
        }

        // THEN
        assertIs<DataState.Loading>(emissions[0])
        assertIs<DataState.Error>(emissions[1])
        assertEquals(DataState.ErrorType.CONNECTION_ERROR, (emissions[1] as DataState.Error).code)
    }


    @Test
    fun  `execute() SHOULD emit an connection_error WHEN connection fails`() = runTest {
        // GIVEN
        whenever(countryService.getAllCountries()).doAnswer { throw httpException404 }

        // WHEN
        val emissions = mutableListOf<DataState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            SUT.execute().toList(emissions)
        }

        // THEN
        assertIs<DataState.Loading>(emissions[0])
        assertIs<DataState.Error>(emissions[1])
        assertEquals(DataState.ErrorType.CONNECTION_ERROR, (emissions[1] as DataState.Error).code)
    }


    @Test
    fun  `execute() SHOULD emit an connection_error WHEN connection is insecure`() = runTest {
        // GIVEN
        whenever(countryService.getAllCountries()).doAnswer { throw SSLHandshakeException("any") }

        // WHEN
        val emissions = mutableListOf<DataState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            SUT.execute().toList(emissions)
        }

        // THEN
        assertIs<DataState.Loading>(emissions[0])
        assertIs<DataState.Error>(emissions[1])
        assertEquals(DataState.ErrorType.CONNECTION_ERROR, (emissions[1] as DataState.Error).code)
    }

    @Test
    fun  `execute() SHOULD emit an connection_slow WHEN connection is slow`() = runTest {
        // GIVEN
        whenever(countryService.getAllCountries()).doAnswer { throw SocketTimeoutException() }

        // WHEN
        val emissions = mutableListOf<DataState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            SUT.execute().toList(emissions)
        }

        // THEN
        assertIs<DataState.Loading>(emissions[0])
        assertIs<DataState.Error>(emissions[1])
        assertEquals(DataState.ErrorType.CONNECTION_SLOW, (emissions[1] as DataState.Error).code)
    }

    @Test
    fun  `execute() SHOULD emit an unknown error WHEN an unknown exception is thrown`() = runTest {
        // GIVEN
        // it could be done with a countryServiceFake instead of a Mock
        // 1. First option
        // Mockito.doAnswer {
        //     throw Exception()
        // }.`when`(countryService).getAllCountries()
        // 2. Second option
        whenever(countryService.getAllCountries()).doAnswer { throw Exception() }

        // WHEN
        val emissions = mutableListOf<DataState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            SUT.execute().toList(emissions)
        }

        // THEN
        assertIs<DataState.Loading>(emissions[0])
        assertIs<DataState.Error>(emissions[1])
        assertEquals(DataState.ErrorType.UNKNOWN, (emissions[1] as DataState.Error).code)
    }

    // Helper methods
    fun setSuccess() = runBlocking {
        whenever(countryService.getAllCountries()).thenReturn(countryList)
        whenever(countryCache.getFavoriteCountries()).thenReturn(favoriteCountries)
    }

    fun setError() = runBlocking {
        whenever(countryService.getAllCountries()).thenThrow()
    }
}
