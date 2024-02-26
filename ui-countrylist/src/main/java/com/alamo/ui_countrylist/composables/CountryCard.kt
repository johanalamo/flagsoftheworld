package com.alamo.ui_countrylist.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alamo.jc_ui_components.PersonalizedIcons

@Composable
fun CountryCard(
    name: String,
    flag: String?,
    codeISO3: String?,
    isFavorite: Boolean,
    onClick: () -> Unit? = { null},
    addToFavorites: () -> Unit? = {null},
    removeFromFavorites: () -> Unit? = {null},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start  = 16.dp, end  = 16.dp, top = 4.dp, bottom = 4.dp),
        onClick = {
            onClick()
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(vertical = 8.dp, horizontal = 8.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .fillMaxWidth(0.1f),
                    text = flag ?: "",
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    text = "$name ($codeISO3)"
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    if (isFavorite) {
                            Icon(modifier = Modifier.clickable { removeFromFavorites() }, imageVector = PersonalizedIcons.IsFavorite, contentDescription = "")
                    } else {
                            Icon(modifier = Modifier.clickable { addToFavorites() }, imageVector = PersonalizedIcons.IsNotFavorite, contentDescription = "",)
                    }
                }
            }

        }
    }
}
