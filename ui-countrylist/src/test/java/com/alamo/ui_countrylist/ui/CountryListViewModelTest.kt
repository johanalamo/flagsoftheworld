package com.alamo.ui_countrylist.ui

import com.alamo.core.domain.DataState
import com.alamo.country_domain.Country
import com.alamo.country_interactors.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
internal class CountryListViewModelTest {

    private lateinit var classUnderTest: CountryListViewModel

    @Mock
    private lateinit var getCountriesUseCaseFake: UseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    // Helper values
    val countryList = listOf<Country>(
        Country(name = "Venezuela", codeISO3 = "VEN", capital = listOf("Caracas")),
        Country(name = "Argentina", codeISO3 = "ARG", capital = listOf("Buenos Aires")),
        Country(name = "Uruguay", codeISO3 = "URY", capital = listOf("Montevideo")),
    )
    // End helper values

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        classUnderTest = CountryListViewModel(getCountriesUseCaseFake, dispatcher = testDispatcher)

        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `onTriggerEvent(GetCountries) SHOULD show a list WHEN collects a success`() = runTest {
        // GIVEN
        whenever(getCountriesUseCaseFake.execute()).thenReturn(flow {
            emit(DataState.Success<List<Country>>(data = countryList))
        })

        // WHEN
        classUnderTest.onTriggerEvent(CountryListEvents.GetCountries)

        // THEN
        assertEquals(
            CountryListState(list = countryList, error = null, isLoading = false),
            classUnderTest.state.value
        )
    }

    @Test
    fun `onTriggerEvent(GetCountries) SHOULD show an error WHEN collects an error`() = runTest {
        // GIVEN
        whenever(getCountriesUseCaseFake.execute()).thenReturn(flow {
            emit(DataState.Error(101, "testing error"))
        })

        // WHEN
        classUnderTest.onTriggerEvent(CountryListEvents.GetCountries)

        // THEN
        assertEquals(
            CountryListState(list = emptyList(), error = Pair(101, "testing error"), isLoading = false),
            classUnderTest.state.value
        )
    }

    @Test
    fun `onTriggerEvent(GetCountries) SHOULD show an empty list WHEN collects an empty list`() = runTest {
        // GIVEN
        whenever(getCountriesUseCaseFake.execute()).thenReturn(flow {
            emit(DataState.Success(data = listOf<Country>()))
        })

        // WHEN
        classUnderTest.onTriggerEvent(CountryListEvents.GetCountries)

        // THEN
        assertEquals(
            CountryListState(list = emptyList(), error = null, isLoading = false),
            classUnderTest.state.value
        )
    }

    @Test
    fun `onTriggerEvent(GetCountries) SHOULD show the loading state WHEN collects a Loading value`() = runTest {
        // GIVEN
        whenever(getCountriesUseCaseFake.execute()).thenReturn(flow {
            emit(DataState.Loading)
        })

        // WHEN
        classUnderTest.onTriggerEvent(CountryListEvents.GetCountries)

        // THEN
        assertEquals(true, classUnderTest.state.value.isLoading)
    }

    @Test
    fun `onTriggerEvent(CloseErrorDialog) SHOULD put error in null WHEN triggers CloseErrorDialogEvent`() = runTest {
        // GIVEN
        whenever(getCountriesUseCaseFake.execute()).thenReturn(flow {
            emit(DataState.Error(9, "any"))
        })

        // WHEN
        classUnderTest.onTriggerEvent(CountryListEvents.CloseErrorDialog)

        // THEN
        assertNull( classUnderTest.state.value.error)
    }
}