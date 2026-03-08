package com.thehalotech.planthealthtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thehalotech.planthealthtracker.data.model.Plants
import com.thehalotech.planthealthtracker.ui.screens.dashboard.DashboardScreen
import com.thehalotech.planthealthtracker.ui.theme.CardBackground
import com.thehalotech.planthealthtracker.ui.theme.LightGreenBackground
import com.thehalotech.planthealthtracker.ui.theme.PlantGreen
import com.thehalotech.planthealthtracker.ui.theme.PlantHealthTrackerTheme
import com.thehalotech.planthealthtracker.ui.theme.SoftText

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlantHealthTrackerTheme {
                DashboardScreen()
            }
        }
    }
}

@Composable
fun PlantCard(plant: Plants, onWaterClicked: () -> Unit) {
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
                    text = plant.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = plant.species,
                    color = SoftText
                )

                Spacer(modifier = Modifier.height(8.dp))

                StatusRow()

                Spacer(modifier = Modifier.height(10.dp))

                val daysToWater =
                    if (plant.daysToWater == 0) {
                        "Water Now"
                    } else {
                        "Water in ${plant.daysToWater} days"
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PlantHealthTrackerTheme {
        //Greeting("Android")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantTopbar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "My Plants 🌱",
                fontWeight = FontWeight.Bold
            )
        },

        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = LightGreenBackground
        )
    )
}

@Composable
fun AddFabBar() {
    FloatingActionButton(onClick = { /*TODO*/ },
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