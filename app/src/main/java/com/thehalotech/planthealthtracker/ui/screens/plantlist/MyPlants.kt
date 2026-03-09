package com.thehalotech.planthealthtracker.ui.screens.plantlist

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.thehalotech.planthealthtracker.R
import com.thehalotech.planthealthtracker.data.local.MyPlantsTable
import com.thehalotech.planthealthtracker.data.model.Plants
import com.thehalotech.planthealthtracker.ui.screens.addplants.AddPlantViewModel
import com.thehalotech.planthealthtracker.ui.theme.CardBackground
import com.thehalotech.planthealthtracker.ui.theme.LightGreenBackground
import com.thehalotech.planthealthtracker.ui.theme.PlantGreen
import com.thehalotech.planthealthtracker.ui.theme.SoftText
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import kotlin.collections.emptyList

@Composable
fun MyPlants(navController: NavController, viewModel: AddPlantViewModel) {

    val plants by viewModel.plants.collectAsState(emptyList())

    val plantsDummy = listOf(
        Plants("Cherry Tomato", "Solanum lycopersicum", 1),
        Plants("Chilli", "Capsicum annuum", 0),
        Plants("Zinnia", "Zinnia elegans", 2),
        Plants("Cherry Tomato", "Solanum lycopersicum", 1),
        Plants("Chilli", "Capsicum annuum", 0),
        Plants("Zinnia", "Zinnia elegans", 2)
    )
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
                MyPlantListCard(plant = plant, onWaterClicked = { /*TODO*/ })

            }

        }

    }
}

@Composable
fun MyPlantListCard(plant: MyPlantsTable, onWaterClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(modifier = Modifier
                .padding(start = 20.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)) {
                Text(
                    text = plant.plantName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = plant.commonName,
                    color = SoftText
                )

                Spacer(modifier = Modifier.height(8.dp))

                StatusRow()

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

            Image(
                painter = painterResource(R.drawable.plant),
                contentDescription = null,
                contentScale = ContentScale.Fit,

                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)
                    .offset(x = (190).dp)
                    .blur(10.dp)
            )
            Image(
                painter = painterResource(R.drawable.plant),
                contentDescription = null,
                contentScale = ContentScale.Fit,

                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)
                    .offset(x = (200).dp)
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
fun StatusRow() {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        StatusCard("☀️ 20%")
        StatusCard("💧 40%")
        StatusCard("🌡 22°C")

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

    Log.i("TheHalotech::", "Last watered: $lastWateredDate \nNext watering: $nextWaterDate")

    return ChronoUnit.DAYS.between(LocalDate.now(), nextWaterDate)
}