package com.alamo.ui_countrydetails.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alamo.jc_ui_components.GenericDialog
import com.alamo.jc_ui_components.Loader
import com.alamo.ui_countrydetails.R
import com.alamo.ui_countrydetails.ui.CountryDetailsEvents
import com.alamo.ui_countrydetails.ui.CountryDetailsState
import com.alamo.ui_countrydetails.util.Message
import java.text.DecimalFormat

@Composable
fun CountryDetails(
    state: CountryDetailsState,
    snackbarHostState: SnackbarHostState,
    events: (CountryDetailsEvents) -> Unit
) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .availableMemoryPercentage(0.25) // Don't know what is recommended?
        .crossfade(true)
        .build()
    if (state.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Loader("Loading from the network")
        }
    }
    if (state.data != null) {
        Column() {
            LabelValue("Region") { TextBold(state.data?.region.orEmpty()) }
            LabelValue("Subregion") { TextBold(state.data?.subregion.orEmpty()) }
            LabelValue("Official name") { TextBold(state.data?.officialName.orEmpty()) }
            LabelValue("Name") { TextBold(state.data?.name.orEmpty()) }
            LabelValue("ISO3 code") { TextBold(state.data?.codeISO3.orEmpty()) }
            LabelValue("Capital") { state.data?.capital?.forEach { TextBold(it) } }
            LabelValue("Population") {
                TextBold(DecimalFormat("#,###").format((state.data?.population ?: 0)))
            }
            LabelValue("Area") {
                val formato = DecimalFormat("#,###")
                val superscript = SpanStyle(
                    baselineShift = BaselineShift.Superscript,
                    fontSize = 12.sp,
                )
                Text(
                    text =
                    buildAnnotatedString {
                        append(formato.format(state.data?.area ?: 0) + " Km")
                        withStyle(superscript) {
                            append("2")
                        }
                    },
                    fontWeight = FontWeight.Bold
                )
            }
//                LabelValue("Gini") { TextBold("**** in standby ********    for the future  ****") }
//                LabelValue("Currencies") { TextBold("**** in standby ********    for the future  ****") }
            LabelValue("Borders") { state.data?.borders?.forEach { TextBold(it) } }
            LabelValue("Lat/Lon") { TextBold(state.data?.latlng?.toString().orEmpty()) }
//                LabelValue("Languages") { TextBold("**** in standby ********    for the future  ****") }
            LabelValue("Fifa code") { TextBold(state.data?.fifa.orEmpty()) }
            LabelValue("Timezones") { state.data?.timezones?.forEach { TextBold(it) } }
            LabelValue("Flag") {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = rememberImagePainter(
                        state.data?.flagUrl.orEmpty(),
                        imageLoader = imageLoader,
                    ),
                    contentDescription = state.data?.flagDescription,
                    contentScale = ContentScale.FillWidth,
                )
            }
            LabelValue("Map") { TextBold(state.data?.name.orEmpty()) }
            LabelValue("Coat of Arms") {
                Image(
                    painter = rememberImagePainter(
                        state.data?.coatOfArmsUrl.orEmpty(),
                        imageLoader = imageLoader,
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                )
            }
        }
    }

    if (state.messages.isNotEmpty()) {
        when (state.messages.first()) {
            is Message.InternetConnectionError -> {
                GenericDialog(
                    title = stringResource(id = R.string.error),
                    description = stringResource(id = R.string.no_internet_connection),
                    onConfirm = {
                        events(CountryDetailsEvents.DismissTopMessage)
                        events(CountryDetailsEvents.GetCountryDetails(state.data?.codeISO3 ?: ""))
                    },
                    onConfirmText = stringResource(id = R.string.retry),
                    onDismiss = {
                        events(CountryDetailsEvents.DismissTopMessage)
                    },
                    onDismissText = stringResource(id = R.string.cancel)
                )
            }

            is Message.UnknownError -> {
                ShowSnackbar(stringResource(id = R.string.unknown_error)) {
                    events(CountryDetailsEvents.DismissTopMessage)
                }
            }

            is Message.AddToFavoritesFailed -> {
                val message = stringResource(id = R.string.country_add_failed, state.data?.name ?: "")
                ShowSnackbar(message) { events(CountryDetailsEvents.DismissTopMessage) }
            }

            is Message.AddedToFavorites -> {
                val message = stringResource(id = R.string.country_added, state.data?.name ?: "")
                ShowTemporalSnackbar(
                    snackbarHostState,
                    message
                ) { events(CountryDetailsEvents.DismissTopMessage) }
            }

            is Message.RemoveFromFavoritesFailed -> {
                val message = stringResource(id = R.string.country_remove_failed, state.data?.name ?: "")
                ShowSnackbar(message) { events(CountryDetailsEvents.DismissTopMessage) }
            }

            is Message.RemovedFromFavorites -> {
                val message = stringResource(id = R.string.country_removed, state.data?.name ?: "")
                ShowTemporalSnackbar(
                    snackbarHostState,
                    message
                ) { events(CountryDetailsEvents.DismissTopMessage) }
            }

            Message.InternetConnectionSlow -> {
                ShowSnackbar(stringResource(id = R.string.internet_connection_slow)) {
                    events(CountryDetailsEvents.DismissTopMessage)
                }
            }
        }
    }
}