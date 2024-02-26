package com.alamo.ui_countrydetails.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LabelValue(label: String, content: @Composable ColumnScope.() -> Unit) {
    Row(verticalAlignment = Alignment.Top) {
        Text(
            modifier = Modifier.fillMaxWidth(0.3f),
            textAlign = TextAlign.Right,
            text = label + ":"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.fillMaxWidth(0.7f)) {
            content()
        }
    }
}