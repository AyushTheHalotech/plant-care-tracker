package com.thehalotech.planthealthtracker.ui.screens.addplants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.thehalotech.planthealthtracker.data.api.RetrofitClient
import com.thehalotech.planthealthtracker.data.model.PlantSearchItem
import com.thehalotech.planthealthtracker.ui.components.addplants.Description
import com.thehalotech.planthealthtracker.ui.components.addplants.LightRequirement
import com.thehalotech.planthealthtracker.ui.components.addplants.PlantNameInput
import com.thehalotech.planthealthtracker.ui.components.addplants.PlantTypeDropdown
import com.thehalotech.planthealthtracker.ui.components.addplants.WateringScheduleDropdown
import com.thehalotech.planthealthtracker.ui.theme.LightGreenBackground

@Composable
fun AddPlantsScreen(navController: NavController) {

    val viewModel: AddPlantViewModel = viewModel(factory = AddPlantViewModelFactory(RetrofitClient.api))

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGreenBackground,
        topBar = {AddPlantTopbar(navController)},
        content = {padding ->
            Column(modifier = Modifier.padding(padding)) {
                PlantNameInput(
                    plantName = viewModel.searchQuery,
                    results = viewModel.searchResults,
                    onNameChange = viewModel::onQueryChange,
                    onPlantSelected = {plant ->
                        viewModel.onPlantSelected(plant)
                    },
                    isLoading = viewModel.isLoading
                )
                PlantTypeDropdown(plantType = viewModel.plantType, onTypeChange = viewModel::updatePlantType)

                WateringScheduleDropdown(schedule = viewModel.wateringSchedule, onScheduleSelected = viewModel::updateWateringRequirement)
                LightRequirement(requirement = viewModel.lightRequirement, onRequirementSelected = viewModel::updateLightRequirement)
                Description(description = viewModel.description, ondescupdate = viewModel::updateDescription)

                AddPlantButton(onClick = { /* Handle add plant button click */ })
            }
        }
    )




}



@Composable
fun AddPlantButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Add Plant")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlantTopbar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Add New Plant 🌱",
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = {navController.popBackStack()}) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
            }
        },

        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = LightGreenBackground
        )
    )

}