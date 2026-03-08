package com.thehalotech.planthealthtracker.ui.components.addplants

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LightRequirement(
    requirement: String,
    onRequirementSelected: (String) -> Unit
) {
    OutlinedTextField(
        value = requirement,
        onValueChange = onRequirementSelected,
        label = { Text("Light Requirement") },
        modifier = Modifier.fillMaxWidth()
    )
}