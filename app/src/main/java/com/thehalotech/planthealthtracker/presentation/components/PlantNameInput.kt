package com.thehalotech.planthealthtracker.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thehalotech.planthealthtracker.domain.model.IdentifiedPlantDomain
import com.thehalotech.planthealthtracker.domain.model.Plant
import kotlin.collections.forEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantNameInput(
    plantName: String,
    results: List<IdentifiedPlantDomain>,
    onNameChange: (String) -> Unit,
    onPlantSelected: (IdentifiedPlantDomain) -> Unit,
    isLoading: Boolean
) {

    var expanded by remember { mutableStateOf(false) }


    Column {
        ExposedDropdownMenuBox(
            expanded = expanded && results.isNotEmpty(),
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = plantName,
                onValueChange = {
                    onNameChange(it)
                    expanded = true
                },
                label = { Text("Search by Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            )

            ExposedDropdownMenu(
                expanded = expanded && results.isNotEmpty(),
                onDismissRequest = { expanded = false }
            ) {
                results.forEach { plant ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                plant.entityName
                            )
                        },
                        onClick = {
                            expanded = false
                            onPlantSelected(plant)
                        }
                    )
                }
            }

        }

    }

}