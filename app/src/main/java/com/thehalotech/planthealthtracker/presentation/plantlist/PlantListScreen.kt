package com.thehalotech.planthealthtracker.presentation.plantlist

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.thehalotech.planthealthtracker.R
import com.thehalotech.planthealthtracker.data.local.PlantEntity
import com.thehalotech.planthealthtracker.domain.model.Plant
import com.thehalotech.planthealthtracker.presentation.theme.CardBackground
import com.thehalotech.planthealthtracker.presentation.theme.LightGreenBackground
import com.thehalotech.planthealthtracker.presentation.theme.PlantGreen
import com.thehalotech.planthealthtracker.presentation.theme.SoftText
import com.thehalotech.planthealthtracker.presentation.util.getMoistureLevel
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.collections.emptyList

@Composable
fun MyPlants(navController: NavController, viewModel: PlantListViewModel) {

    val plants by viewModel.plants.collectAsState(emptyList())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGreenBackground,
        topBar = {
            PlantTopbar(navController)
        },

        floatingActionButton = {
            AddFabBar(navController)
        }
    ) { padding ->

        LazyColumn(modifier = Modifier.padding(padding)) {
            items(plants) { plant ->
                MyPlantListCard(plant = plant, navController, onWaterClicked = {
                    viewModel.markWatered(plant.id)
                })

            }

        }

    }
}

@Composable
fun MyPlantListCard(plant: Plant, navController: NavController, onWaterClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        onClick = {
            navController.navigate("details/${plant.id}")
        }
    ) {
        Row(
            modifier = Modifier.height(210.dp).fillMaxWidth()
        ) {

            Column(modifier = Modifier
                .padding(18.dp)
                .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = plant.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = plant.commonName,
                    color = SoftText,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                StatusRow(plant)

                Spacer(modifier = Modifier.height(10.dp))


                val daysUntilWater = daysUntilNextWater(plant.lastWatered, plant.wateringFrequency.toInt())
                Log.i("TheHalotech::", "Days until water: $daysUntilWater")
                val daysToWater =
                    if (daysUntilWater < 0) {
                        "Water Now"
                    } else {
                        "Water in $daysUntilWater days"
                    }

                Text(
                    text = daysToWater,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))

                Button(onClick = onWaterClicked,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PlantGreen) ){
                    Text(text = "Mark Watered")
                }

            }

            Spacer(modifier = Modifier.height(12.dp))
            AsyncImage(
                model = plant.imagePath?: R.drawable.plant,
                contentDescription = "Plant",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(140.dp)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(24.dp))

            )



        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantTopbar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "My Plants 🌱",
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

@Composable
fun AddFabBar(navController: NavController) {
    FloatingActionButton(onClick = { navController.navigate("add_plants") },
        containerColor = PlantGreen
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add")
    }
}

@Composable
fun StatusRow(plant: Plant) {
    val moistureLevel = getMoistureLevel(plant.wateringFrequency)
    val lightData = plant.lightRequirement.substringBefore('.')
    val lightRequirement = when {
        lightData.contains("bright, indirect light") -> "50"
        lightData.contains("full sun") -> "90"
        lightData.contains("shade") -> "20"
        else -> "40"
    }
    val tempRequired = when {
        lightData.contains("bright, indirect light") -> "24"
        lightData.contains("full sun") -> "30"
        lightData.contains("shade") -> "19"
        else -> "22"
    }
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        StatusCard("☀️ $lightRequirement%")
        StatusCard("💧 $moistureLevel%")
        StatusCard("🌡 ${tempRequired}°C")

    }
}

@Composable
fun StatusCard(text: String) {
    Box(modifier = Modifier
        .padding(horizontal = 5.dp, vertical = 4.dp)
        .background(color = LightGreenBackground, shape = RoundedCornerShape(8.dp))) {
        Text(
            text = text,
            fontSize = 12.sp,
            modifier = Modifier.padding(2.dp)
        )
    }
}

fun epochToLocalDate(epoch: Long): LocalDate {

    return LocalDate.ofEpochDay(epoch)
}

fun daysUntilNextWater(lastWateredEpoch: Long, frequency: Int): Long {

    val lastWateredDate = epochToLocalDate(lastWateredEpoch)

    val nextWaterDate = lastWateredDate.plusDays(frequency.toLong())

    return ChronoUnit.DAYS.between(LocalDate.now(), nextWaterDate)
}