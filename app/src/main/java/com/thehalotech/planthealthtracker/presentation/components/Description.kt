package com.thehalotech.planthealthtracker.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Description(
    description: String,
    ondescupdate: (String) -> Unit
) {
    OutlinedTextField(
        value = description,
        onValueChange = ondescupdate,
        label = { Text("Description") },
        modifier = Modifier.fillMaxWidth()
    )
}