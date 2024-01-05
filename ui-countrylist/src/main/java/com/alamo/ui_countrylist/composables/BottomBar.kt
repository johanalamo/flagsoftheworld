package com.alamo.ui_countrylist.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar() {
    var selected by remember { mutableStateOf(0) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomBarIcon(
            option = BottomBarOption(
                id = 0,
                image = Icons.Default.Home,
                title = "Home",
                contentDescription = "Home",
                selected = (selected == 0),
                onClick = { selected = 0 }
            )
        )
        BottomBarIcon(
            option = BottomBarOption(
                id = 1,
                image = Icons.Default.Place,
                title = "Map",
                selected = (selected == 1),
                contentDescription = "Map",
                onClick = { selected = 1 }
            )
        )
        BottomBarIcon(
            option = BottomBarOption(
                id = 2,
                image = Icons.Default.Menu,
                title = "Menu",
                selected = (selected == 2),
                contentDescription = "Menu",
                onClick = { selected = 2 }
            )
        )
    }
}

@Composable
fun BottomBarIcon(option: BottomBarOption) {
    val colors = MaterialTheme.colorScheme
    val tintColor = if (option.selected) {
        colors.primary
    } else {
        colors.secondary
    }
    Surface(onClick = {
        option.onClick()
    }) {
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = option.image, tint = tintColor, contentDescription = option.contentDescription)
            Text(text = option.title, color = tintColor)
        }
    }
}

data class BottomBarOption(
    val id: Int,
    val image: ImageVector,
    val title: String,
    val contentDescription: String,
    var selected: Boolean = false,
    val onClick: () -> Unit,
)


