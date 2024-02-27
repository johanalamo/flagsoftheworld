package com.alamo.country_interactors

import com.alamo.core.domain.DataState
import com.alamo.country_datasource.cache.CountryCache
import com.alamo.country_datasource.network.CountryService
import com.alamo.country_datasource.network.model.toCountryDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.reflect.typeOf

class GetCountryDetailsUseCase(
    private val countryService: CountryService,
    private val countryCache: CountryCache,
) : UseCase {

    override suspend fun execute(vararg parameters: String): Flow<DataState> = withContext(Dispatchers.IO) {
        flow {
            try {
                val countryCode = parameters[0]
                emit(DataState.Loading)

                val countryData = countryService.getCountryDetails(countryCode).map {
                    it.toCountryDetails()
                }.first()

                countryData.isFavorite = countryCache.isFavorite(countryCode)
                emit(DataState.Success(
                    data = countryData
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

            // An error has occurred trying to connect
            is java.net.ConnectException,

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
