package com.alamo.ui_countrylist.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun CountryCardPreview() {
    CountryCard(
        name = "Venezuela",
        region = "America",
        subregion = "Suramerica",
        flag = "🇻🇪",
        population = 28001002,
        onClick = {},
    )
}