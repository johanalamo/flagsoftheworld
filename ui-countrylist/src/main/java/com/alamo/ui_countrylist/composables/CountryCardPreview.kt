package com.alamo.ui_countrylist.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showSystemUi = true,
    backgroundColor = 0xff00ff,
    showBackground = true,
    device = "id:pixel_6_pro",

)
@Composable
fun CountryCardPreview() {
    CountryCard(
        name = "Venezuela",
        codeISO3 = "VEN",
        region = "America",
        subregion = "Suramerica",
        flag = "🇻🇪",
        isFavorite = true,
        onClick = {},
    )
}