package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.cache.CountryCache
import com.alamo.country_datasource.network.CountryService
import com.alamo.country_datasource.network.model.toCountry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetCountriesUseCase(
    private val countryService: CountryService,
    private val countryCache: CountryCache,
) : UseCase {

    override fun execute(vararg parameters: String): Flow<DataState> {
        return flow {
            try {
                emit(DataState.Loading)
                val list = countryService.getAllCountries().map { it.toCountry() }
                val favorites = countryCache.getFavoriteCountries()
                list.forEach {
                    it.isFavorite = (favorites.contains(it.codeISO3))
                }
                emit(DataState.Success(
                    data = list
                ))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(DataState.Error(code = mapToErrorType(e)))
            }
        }
    }

    private fun mapToErrorType(e: Exception): DataState.ErrorType {
        return when (e) {
            // when there is a non-null field in the Dto and it does not come from the server
            is java.lang.NullPointerException,

            // No connection and broken url / no server at all
            is java.net.UnknownHostException,

            // A network connection detected as insecure
            is javax.net.ssl.SSLHandshakeException,

            // Retrofit exception, server error response (404, 300, 301, 500, etc)
            // https://github.com/square/retrofit/blob/master/retrofit/src/main/java/retrofit2/HttpException.java
            is HttpException -> DataState.ErrorType.CONNECTION_ERROR

            // Server takes too long to respond
            is java.net.SocketTimeoutException ->  DataState.ErrorType.CONNECTION_SLOW

            // other
            else ->  DataState.ErrorType.UNKNOWN
        }
    }
}
