package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.network.CountryService
import com.alamo.country_datasource.network.model.CountryDto
import com.alamo.country_datasource.network.model.CountryNameDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlin.test.assertIs
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class GetCountriesUseCaseTest {

    // Helper values
    val countryList = listOf<CountryDto>(
        CountryDto(name = CountryNameDto(common = "Venezuela"), codeISO3 = "VEN", capital = listOf("Caracas")),
        CountryDto(name = CountryNameDto(common = "Argentina"), codeISO3 = "ARG", capital = listOf("Buenos Aires")),
        CountryDto(name = CountryNameDto(common = "Uruguay"), codeISO3 = "URY", capital = listOf("Montevideo")),
    )

    // End Helper values

    lateinit var SUT: GetCountriesUseCase

    @Mock
    lateinit var countryService: CountryService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        SUT = GetCountriesUseCase(countryService)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun  `execute() SHOULD return a country list WHEN it is connected`() = runTest {
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
    fun  `execute() SHOULD emit an error WHEN a network connection happens`() = runTest {
        // GIVEN
        setError()

        // WHEN
        val emissions = mutableListOf<DataState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            SUT.execute().toList(emissions)
        }

        // THEN
        assertIs<DataState.Loading>(emissions[0])
        assertIs<DataState.Error>(emissions[1])
    }

    // Helper methods
    fun setSuccess() = runBlocking {
        whenever(countryService.getAllCountries()).thenReturn(countryList)
    }

    fun setError() = runBlocking {
        whenever(countryService.getAllCountries()).thenThrow()
    }
}