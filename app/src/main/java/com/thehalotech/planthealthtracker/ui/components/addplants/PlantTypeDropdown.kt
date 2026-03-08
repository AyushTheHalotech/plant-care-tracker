package com.thehalotech.planthealthtracker.ui.components.addplants

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PlantTypeDropdown(plantType: String, onTypeChange: (String) -> Unit) {
    OutlinedTextField(
        value = plantType,
        onValueChange = onTypeChange,
        label = {Text("Scientific Name")},
        modifier = Modifier.fillMaxWidth()
    )
}