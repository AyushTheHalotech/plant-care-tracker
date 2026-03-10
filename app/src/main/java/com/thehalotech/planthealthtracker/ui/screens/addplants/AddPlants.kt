package com.thehalotech.planthealthtracker.ui.screens.addplants

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.thehalotech.planthealthtracker.application.PlantApp
import com.thehalotech.planthealthtracker.data.api.RetrofitClient
import com.thehalotech.planthealthtracker.ui.components.addplants.Description
import com.thehalotech.planthealthtracker.ui.components.addplants.LightRequirement
import com.thehalotech.planthealthtracker.ui.components.addplants.PlantImagePicker
import com.thehalotech.planthealthtracker.ui.components.addplants.PlantNameInput
import com.thehalotech.planthealthtracker.ui.components.addplants.PlantTypeDropdown
import com.thehalotech.planthealthtracker.ui.components.addplants.WateringScheduleDropdown
import com.thehalotech.planthealthtracker.ui.theme.LightGreenBackground
import com.thehalotech.planthealthtracker.ui.theme.PlantGreen

@Composable
fun AddPlantsScreen(navController: NavController, viewModel: AddPlantViewModel) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        uri ->
        uri?.let {
            viewModel.updateSelectedImageUri(it)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGreenBackground,
        topBar = {AddPlantTopbar(navController)},
        content = {padding ->
            Column(modifier = Modifier.padding(padding)) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    PlantImagePicker(
                        uri = viewModel.selectedImageUri,
                        onImageSelected = {
                            launcher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly))

                        }
                    )

                    PlantNameInput(
                        plantName = viewModel.searchQuery,
                        results = viewModel.searchResults,
                        onNameChange = viewModel::onQueryChange,
                        onPlantSelected = { plant ->
                            viewModel.onPlantSelected(plant)
                        },
                        isLoading = viewModel.isLoading
                    )
                    PlantTypeDropdown(plantType = viewModel.plantType, onTypeChange = viewModel::updatePlantType)

                    WateringScheduleDropdown(schedule = viewModel.wateringSchedule, onScheduleSelected = viewModel::updateWateringRequirement)
                    LightRequirement(requirement = viewModel.lightRequirement, onRequirementSelected = viewModel::updateLightRequirement)
                    Description(description = viewModel.description, ondescupdate = viewModel::updateDescription)
                }

                AddPlantButton(onClick = {
                    viewModel.addPlant{navController.popBackStack()}

                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp))
            }
        }
    )

}

@Composable
fun AddPlantButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = PlantGreen,
            contentColor = Color.White
        )
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