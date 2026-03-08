package com.thehalotech.planthealthtracker.ui.components.addplants

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WateringScheduleDropdown(
    schedule: String,
    onScheduleSelected: (String) -> Unit
) {
    OutlinedTextField(
        value = schedule,
        onValueChange = onScheduleSelected,
        label = { Text("Watering Schedule") },
        modifier = Modifier.fillMaxWidth()
    )
}