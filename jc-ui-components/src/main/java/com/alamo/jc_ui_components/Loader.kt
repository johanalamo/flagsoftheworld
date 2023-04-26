package com.alamo.jc_ui_components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Loader(message: String? = null) {
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        message?.let {
            Text(
                text = it,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 6.dp),
            )
        }
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 10.dp)
        )
    }
}

@Composable
@Preview
fun LoaderPreview() {
    Loader("Loading from the network")
}