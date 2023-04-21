package com.alamo.ui_countrylist.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryCard(
    name: String?,
    region: String?,
    subregion: String?,
    flag: String?,
    population: Long?,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, color = Color.Red),
        onClick = {
            onClick()
        },
    ) {
        Column {
            Row {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = flag ?: "",
                )
                Text(text = name ?: "undefinded")
            }
            Text(text = region ?: "no_region")
            Text(text = subregion ?: "no_subregion")
            Row {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = "Population:",
                )
                Text(text = population.toString())
            }
        }
    }
}
