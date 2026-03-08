package com.thehalotech.planthealthtracker.ui.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thehalotech.planthealthtracker.R
import com.thehalotech.planthealthtracker.ui.theme.CardBackground
import com.thehalotech.planthealthtracker.ui.theme.LightGreenBackground
import com.thehalotech.planthealthtracker.ui.theme.PlantGreen

@Preview
@Composable
fun DashboardScreen() {

    Column(modifier = Modifier.fillMaxSize()
        .background(color = LightGreenBackground)) {
        ProfileHeader()
        Spacer(modifier = Modifier.height(16.dp))
        HeroPlantCard()
        Spacer(modifier = Modifier.height(16.dp))
        PlantList()

    }

}

@Composable
fun ProfileHeader() {
    Row(modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 30.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(R.drawable.profile),
            contentDescription = "Plant",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.clip(CircleShape)
                .size(50.dp).border(1.dp, PlantGreen, CircleShape))

        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(text = "Hi Ayush!",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp)
            Text(text = "Welcome Back!")
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { },
            modifier = Modifier.background(color = LightGreenBackground, shape = CircleShape)
                .border(1.dp, PlantGreen, CircleShape).size(30.dp)) {
            Icon(Icons.Default.Notifications, contentDescription = null)
        }

    }
}

@Composable
fun HeroPlantCard() {
    Card(modifier = Modifier.fillMaxWidth().padding(20.dp).height(150.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)) {

        Row(modifier = Modifier.padding(5.dp)) {

            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "Bengaluru, India",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp)
                Text(text = "Your plants are doing well",
                    fontSize = 12.sp)

                Row(modifier = Modifier.padding(top = 10.dp)
                    .height(100.dp)) {
                    WeatherCard(stat = "Humidity", value = "80%")
                    Spacer(modifier = Modifier.width(10.dp))
                    WeatherCard(stat = "UV Index", value = "2/10")
                    Spacer(modifier = Modifier.width(10.dp))
                    WeatherCard(stat = "Precip.", value = "10%")

                }
            }

            Box(modifier = Modifier.weight(1f).fillMaxHeight()
                .align(Alignment.CenterVertically)) {
                Image(painter = painterResource(R.drawable.plant),
                    contentDescription = null,
                    contentScale = ContentScale.Crop

                )
            }
        }

    }
}

@Composable
fun WeatherCard(stat: String, value: String) {

    Card(modifier = Modifier.fillMaxHeight()
        .width(70.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)) {

        Column(modifier = Modifier.fillMaxSize().padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stat,
                fontSize = 10.sp,)
            Text(text= value,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,)
        }

    }

}

@Composable
fun PlantList() {

    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Latest Addition")

        Text(text = "Show All >>>",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { /*TODO*/ }
        )
    }

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            PlantCard()
        }
        item {
            PlantCard()
        }
        item {
            PlantCard()
        }
        item {
            PlantCard()
        }
        item {
            PlantCard()
        }
        item {
            PlantCard()
        }
        item {
            PlantCard()
        }
        item {
            PlantCard()
        }
        item {
            PlantCard()
        }
        item {
            PlantCard()
        }
    }

}

@Composable
fun PlantCard() {
    Card(modifier = Modifier.fillMaxWidth().height(180.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground))  {
        Column(modifier = Modifier.padding(10.dp).fillMaxSize()) {
            Image(painter = painterResource(R.drawable.plant),
                contentDescription = "Plant",
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally).weight(1f),
                contentScale = ContentScale.Crop)
            Text(
                text = "Cherry Tomato",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "Water in 2 days",
                fontSize = 12.sp
            )
        }

    }
}