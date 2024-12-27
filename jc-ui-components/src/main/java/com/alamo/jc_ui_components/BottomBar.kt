package com.alamo.jc_ui_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(
    navigateToCountryListScreen: () -> Unit = { null },
    navigateToMapScreen: () -> Unit = { null },
    navigateToSettingsScreen: () -> Unit = { null },

    ) {
    var selected by remember { mutableStateOf(0) }


    Column {
        Spacer(modifier = Modifier.height(4.dp))
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomBarIcon(
                option = BottomBarOption(
                    id = 0,
                    image = Icons.Default.Menu,
                    title = "Countries",
                    contentDescription = "Countries",
                    selected = (selected == 0),
                    onClick = {
                        navigateToCountryListScreen()
                        selected = 0
                    }
                )
            )
            BottomBarIcon(
                option = BottomBarOption(
                    id = 1,
                    image = Icons.Default.Place,
                    title = "Map",
                    selected = (selected == 1),
                    contentDescription = "Map",
                    onClick = {
                        navigateToMapScreen()
                        selected = 1
                    }
                )
            )
            BottomBarIcon(
                option = BottomBarOption(
                    id = 2,
                    image = Icons.Default.Settings,
                    title = "Settings",
                    selected = (selected == 2),
                    contentDescription = "Settings",
                    onClick = {
                        navigateToSettingsScreen()
                        selected = 2
                    }
                )
            )
        }
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


