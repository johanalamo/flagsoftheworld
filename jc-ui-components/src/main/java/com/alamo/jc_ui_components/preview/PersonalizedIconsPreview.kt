package com.alamo.jc_ui_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.alamo.jc_ui_components.PersonalizedIcons

@Composable
@Preview
fun PersonalizedIconsPreview() {
    Column(
        modifier = Modifier
            .background(color = Color.Cyan),
    ) {
        Icon(PersonalizedIcons.IsNotFavorite, contentDescription = "",)
        Icon(PersonalizedIcons.IsFavorite, contentDescription = "",)
    }
}
