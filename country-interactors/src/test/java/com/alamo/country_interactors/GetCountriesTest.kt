package com.alamo.country_interactors

import com.alamo.country_datasource.network.CountryService
import com.alamo.country_datasource.network.model.CountryDto
import com.alamo.country_datasource.network.model.CountryNameDto
import com.alamo.country_domain.Country
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCountriesTest {

    // TODO: 2023-05-26 tests must be implemented

    lateinit var SUT: GetCountries

    @Before
    fun setUp() {
        SUT = GetCountries(
            object : CountryService {
                override suspend fun getAllCountries(): List<CountryDto> {
                    return listOf(
                        CountryDto(name = CountryNameDto(common = "Venezuela")),
                        CountryDto(name = CountryNameDto(common = "Argentina")),
                        CountryDto(name = CountryNameDto(common = "Uruguay")),
                    )
                }
            }
        )
    }

    @Test
    fun test2() {
        assert(true)
    }
    @Test
    fun test3() {
        assert(true)
    }
    @Test
    fun test4() {
        assert(true)
    }
}