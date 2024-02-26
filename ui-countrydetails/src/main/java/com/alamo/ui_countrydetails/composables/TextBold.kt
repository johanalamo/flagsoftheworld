package com.alamo.ui_countrydetails.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TextBold(text: String) {
    Text(fontWeight = FontWeight.Bold, text = text)
}