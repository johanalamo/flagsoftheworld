package com.alamo.jc_ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Loader(message: String? = null) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        message?.let {
            Text(
                text = it,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .alpha(0.7f)
                    .fillMaxWidth()
                    .background(color = Color.White),
                fontSize = 10.sp
            )
        }
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 10.dp)
        )
    }
}
