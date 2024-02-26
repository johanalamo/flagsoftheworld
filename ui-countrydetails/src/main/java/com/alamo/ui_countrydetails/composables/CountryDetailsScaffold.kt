package com.alamo.ui_countrydetails.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.alamo.jc_ui_components.Loader
import com.alamo.jc_ui_components.PersonalizedIcons
import com.alamo.ui_countrydetails.ui.CountryDetailsEvents
import com.alamo.ui_countrydetails.ui.CountryDetailsState
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun CountryDetailsScaffold(
    state: CountryDetailsState,
    events: (CountryDetailsEvents) -> Unit,
    navigateBack: () -> Unit
) {
    val snackbarHostState = SnackbarHostState()
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .availableMemoryPercentage(0.25) // Don't know what is recommended?
        .crossfade(true)
        .build()
    Scaffold(topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }, title = {
            Text(text = state.data?.name.orEmpty())
        }, actions = {
            IconButton(onClick = { }) {
                if (state.data?.isFavorite == true) {
                    Icon(imageVector = PersonalizedIcons.IsFavorite, contentDescription = null)
                } else {
                    Icon(imageVector = PersonalizedIcons.IsNotFavorite, contentDescription = null)
                }
            }
        })
    }, content = { paddingValues ->
        if (state.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Loader("Loading from the network")
            }
        } else {
            Column(modifier = Modifier.padding(paddingValues)) {
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
                LabelValue("Gini") { TextBold("**** in standby ********") }
                LabelValue("Currencies") { TextBold("**** in standby ********") }
                LabelValue("Borders") { state.data?.borders?.forEach { TextBold(it) } }
                LabelValue("Lat/Lon") { TextBold(state.data?.latlng?.toString().orEmpty()) }
                LabelValue("Languages") { TextBold("**** in standby ********") }
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
    }, snackbarHost = {
        SnackbarHost(snackbarHostState) { snackbarData ->
            Snackbar(
                snackbarData, modifier = Modifier.padding(16.dp)
            )
        }
    })
}
