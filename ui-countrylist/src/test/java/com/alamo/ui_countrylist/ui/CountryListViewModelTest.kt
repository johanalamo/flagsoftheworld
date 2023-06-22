package com.alamo.ui_countrylist.ui

import com.alamo.core.domain.DataState
import com.alamo.country_domain.Country
import com.alamo.country_interactors.UseCase
import com.alamo.ui_countrylist.util.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.util.LinkedList
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class CountryListViewModelTest {

    private lateinit var classUnderTest: CountryListViewModel

    @Mock
    private lateinit var getCountriesUseCaseFake: UseCase

    @Mock
    private lateinit var addCountryToFavoritesUseCaseFake: UseCase

    @Mock
    private lateinit var removeCountryFromFavoritesUseCaseFake: UseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    // Helper values
    val countryList = listOf<Country>(
        Country(name = "Venezuela", codeISO3 = "VEN", capital = listOf("Caracas"), isFavorite = true),
        Country(name = "Argentina", codeISO3 = "ARG", capital = listOf("Buenos Aires")),
        Country(name = "Uruguay", codeISO3 = "URY", capital = listOf("Montevideo")),
    )
    // End helper values

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        classUnderTest = CountryListViewModel(
            getCountriesUseCase = getCountriesUseCaseFake,
            addCountryToFavoritesUseCase = addCountryToFavoritesUseCaseFake,
            removeCountryFromFavoritesUseCase = removeCountryFromFavoritesUseCaseFake,
            dispatcher = testDispatcher,
        )

        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `onTriggerEvent(GetCountries) SHOULD show a list WHEN collects a success`() = runTest {
        // GIVEN
        whenever(getCountriesUseCaseFake.execute()).thenReturn(flow {
            emit(DataState.Success<List<Country>>(data = countryList))
        })

        // WHEN
        classUnderTest.triggerEvent(CountryListEvents.GetCountries)

        // THEN
        assertEquals(
            CountryListState(list = countryList, messages = LinkedList<Message>(), isLoading = false),
            classUnderTest.state.value
        )
    }

    @Test
    fun `onTriggerEvent(GetCountries) SHOULD show an connection error WHEN collects an connection error`() = runTest {
        // GIVEN
        whenever(getCountriesUseCaseFake.execute()).thenReturn(flow {
            emit(DataState.Error(DataState.ErrorType.CONNECTION_ERROR))
        })

        // WHEN
        classUnderTest.triggerEvent(CountryListEvents.GetCountries)

        // THEN
        assertEquals(
            CountryListState(list = emptyList(), messages = LinkedList<Message>(listOf(Message.InternetConnectionError)), isLoading = false),
            classUnderTest.state.value
        )
    }


    @Test
    fun `onTriggerEvent(GetCountries) SHOULD show a connection slow message WHEN collects a connection slow error`() = runTest {
        // GIVEN
        whenever(getCountriesUseCaseFake.execute()).thenReturn(flow {
            emit(DataState.Error(DataState.ErrorType.CONNECTION_SLOW))
        })

        // WHEN
        classUnderTest.triggerEvent(CountryListEvents.GetCountries)

        // THEN
        assertEquals(
            CountryListState(list = emptyList(), messages = LinkedList<Message>(listOf(Message.InternetConnectionSlow)), isLoading = false),
            classUnderTest.state.value
        )
    }

    @Test
    fun `onTriggerEvent(GetCountries) SHOULD show an unknown error message WHEN collects an unknown error`() = runTest {
        // GIVEN
        whenever(getCountriesUseCaseFake.execute()).thenReturn(flow {
            emit(DataState.Error(DataState.ErrorType.UNKNOWN))
        })

        // WHEN
        classUnderTest.triggerEvent(CountryListEvents.GetCountries)

        // THEN
        assertEquals(
            CountryListState(list = emptyList(), messages = LinkedList<Message>(listOf(Message.UnknownError)), isLoading = false),
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
        classUnderTest.triggerEvent(CountryListEvents.GetCountries)

        // THEN
        assertEquals(
            CountryListState(list = emptyList(), isLoading = false),
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
        classUnderTest.triggerEvent(CountryListEvents.GetCountries)

        // THEN
        assertEquals(true, classUnderTest.state.value.isLoading)
    }

    @Test
    fun `onTriggerEvent(AddCountryToFavorites) SHOULD add the country, update the list and show a message WHEN it is successful`() = runTest {
        val newFavorite = "URY"

        // GIVEN
        whenever(getCountriesUseCaseFake.execute()).thenReturn(flow {
            emit(DataState.Success<List<Country>>(data = countryList))
        })
        whenever(addCountryToFavoritesUseCaseFake.execute(anyString())).thenReturn(flow {
            emit(DataState.Loading)
            emit(DataState.Success<Nothing>())
        })
        classUnderTest.triggerEvent(CountryListEvents.GetCountries)

        // WHEN
        classUnderTest.triggerEvent(CountryListEvents.AddUserCountryToFavorites(newFavorite))

        // THEN
        assertTrue(classUnderTest.state.value.list.first { it.codeISO3 == newFavorite }.isFavorite)
        assertIs<Message.AddedToFavorites>(classUnderTest.state.value.messages.first())
        assertEquals(
            newFavorite,
            (classUnderTest.state.value.messages.first() as Message.AddedToFavorites).countryCode
        )
    }


    @Test
    fun `onTriggerEvent(AddCountryToFavorites) SHOULD hide the loader and show a message WHEN an error occurs `() = runTest {
        val newFavorite = "URY"

        // GIVEN
        whenever(getCountriesUseCaseFake.execute()).thenReturn(flow {
            emit(DataState.Success<List<Country>>(data = countryList))
        })
        whenever(addCountryToFavoritesUseCaseFake.execute(anyString())).thenReturn(flow {
            emit(DataState.Loading)
            emit(DataState.Error(DataState.ErrorType.CONNECTION_ERROR))
        })
        classUnderTest.triggerEvent(CountryListEvents.GetCountries)

        // WHEN
        classUnderTest.triggerEvent(CountryListEvents.AddUserCountryToFavorites(newFavorite))

        // THEN
        assertFalse(classUnderTest.state.value.isLoading)
        assertIs<Message.AddToFavoritesFailed>(classUnderTest.state.value.messages.first())
        assertEquals(
            newFavorite,
            (classUnderTest.state.value.messages.first() as Message.AddToFavoritesFailed).countryCode
        )
    }

    @Test
    fun `onTriggerEvent(RemoveCountryFromFavorites) SHOULD remove the country, update the list and show a message WHEN successful`() = runTest {
        val countryToRemove = "VEN"

        // GIVEN
        whenever(getCountriesUseCaseFake.execute()).thenReturn(flow {
            emit(DataState.Success<List<Country>>(data = countryList))
        })
        whenever(removeCountryFromFavoritesUseCaseFake.execute(anyString())).thenReturn(flow {
            emit(DataState.Loading)
            emit(DataState.Success<Nothing>())
        })
        classUnderTest.triggerEvent(CountryListEvents.GetCountries)

        // WHEN
        classUnderTest.triggerEvent(CountryListEvents.RemoveUserCountryFromFavorites(countryToRemove))

        // THEN
        assertFalse(classUnderTest.state.value.list.first { it.codeISO3 == countryToRemove }.isFavorite)
        assertIs<Message.RemovedFromFavorites>(classUnderTest.state.value.messages.first())
        assertEquals(
            countryToRemove,
            (classUnderTest.state.value.messages.first() as Message.RemovedFromFavorites).countryCode
        )
    }
    @Test
    fun `onTriggerEvent(RemoveCountryFromFavorites) SHOULD hide loader and show a message WHEN an error happens`() = runTest {
        val countryToRemove = "VEN"

        // GIVEN
        whenever(getCountriesUseCaseFake.execute()).thenReturn(flow {
            emit(DataState.Success<List<Country>>(data = countryList))
        })
        whenever(removeCountryFromFavoritesUseCaseFake.execute(anyString())).thenReturn(flow {
            emit(DataState.Loading)
            emit(DataState.Error(DataState.ErrorType.CONNECTION_ERROR))
        })
        classUnderTest.triggerEvent(CountryListEvents.GetCountries)

        // WHEN
        classUnderTest.triggerEvent(CountryListEvents.RemoveUserCountryFromFavorites(countryToRemove))

        // THEN
        assertFalse(classUnderTest.state.value.isLoading)
        assertIs<Message.RemoveFromFavoritesFailed>(classUnderTest.state.value.messages.first())
        assertEquals(
            countryToRemove,
            (classUnderTest.state.value.messages.first() as Message.RemoveFromFavoritesFailed).countryCode
        )
    }

    @Test
    fun `onTriggerEvent(DismissTopMessage) SHOULD put error in null WHEN triggers CloseErrorDialogEvent`() = runTest {
        // GIVEN
        whenever(getCountriesUseCaseFake.execute()).thenReturn(flow {
            emit(DataState.Error(DataState.ErrorType.CONNECTION_ERROR))
        })
        classUnderTest.triggerEvent(CountryListEvents.GetCountries)

        // checks that the message was really emited
        assertEquals(1, classUnderTest.state.value.messages.size)


        // WHEN
        classUnderTest.triggerEvent(CountryListEvents.DismissTopMessage)

        // THEN
        assert(classUnderTest.state.value.messages.isEmpty())
    }
}
