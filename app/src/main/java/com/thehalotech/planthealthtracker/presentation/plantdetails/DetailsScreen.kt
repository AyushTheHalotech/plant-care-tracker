package com.thehalotech.planthealthtracker.presentation.plantdetails

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.thehalotech.planthealthtracker.R
import com.thehalotech.planthealthtracker.data.local.PlantEntity
import com.thehalotech.planthealthtracker.domain.model.Plant
import com.thehalotech.planthealthtracker.presentation.plantlist.epochToLocalDate
import com.thehalotech.planthealthtracker.presentation.theme.CardBackground
import com.thehalotech.planthealthtracker.presentation.theme.LightGreenBackground
import com.thehalotech.planthealthtracker.presentation.theme.PlantGreen
import com.thehalotech.planthealthtracker.presentation.util.getMoistureLevel
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit



@Composable
fun DetailsScreen(viewModel: PlantDetailsViewModel, navController: NavController) {

    val plant by viewModel.plant.collectAsState()

    plant?.let {
        p -> MainData(p, navController, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainData(p : Plant, navController: NavController, viewModel: PlantDetailsViewModel) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val plantName = p.name
    val description = p.description
    val lastWatered = p.lastWatered
    val wateringFrequency = p.wateringFrequency
    val lightRequirement = p.lightRequirement
    val commonName =p.commonName.uppercase()
    val imagePath = p.imagePath

    val daysUntilWater = daysUntilWater(lastWatered, wateringFrequency)
    val lastWateredDate = epochToDate(lastWatered)


    val moistureLevel = getMoistureLevel(wateringFrequency)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = LightGreenBackground,
        topBar = {
            DetailsTopBar(plantName.uppercase(), scrollBehavior, navController)
        }
    ) {padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {

            item {
                CollapsableImageHeader(imagePath)
            }
            item {
                Column(modifier = Modifier.offset(y = (-80).dp)) {
                    StatCard(moisture = moistureLevel, lastW = lastWateredDate, waterIn = daysUntilWater,modifier = Modifier.fillMaxWidth().padding(20.dp))
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(modifier = Modifier
                        .padding(start = 20.dp),
                        text="Commonly called $commonName".uppercase(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp)
                    HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(10.dp))
                    Spacer(modifier = Modifier.height(20.dp))
                    PlantDeets(description)
                    HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(10.dp))
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(modifier = Modifier
                        .padding(start = 20.dp),
                        text="Light Requirements",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(20.dp))
                    PlantDeets(lightRequirement)
                    HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(10.dp))
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {onWaterClicked(p.id, viewModel)},
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PlantGreen),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            ){
                        Icon(Icons.Default.WaterDrop, contentDescription = "Water")
                        Text(text = "Water Me!",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(10.dp))
                    }
                }

            }
        }

    }

}

fun onWaterClicked(name: String, viewModel: PlantDetailsViewModel) {

    name.let {
        viewModel.onWateredClicked(it)
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(plantName: String, scrollBehavior: TopAppBarScrollBehavior, navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(plantName.uppercase(),
                fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
            IconButton(onClick = {navController.popBackStack()}
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Transparent
        )
    )
}

@Composable
fun PlantDeets(details: String) {
    Column(modifier = Modifier.fillMaxSize()
        .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),) {

        Text(text = details,
            textAlign = TextAlign.Justify,
            fontStyle = FontStyle.Italic,
            fontSize = 12.sp,
        )


    }

}

@Composable
fun StatCard(moisture: Int, lastW: String, waterIn: Long, modifier: Modifier) {
    Card(
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(40.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier
                .padding(20.dp)){
                CircularProgressWithText(value = moisture)
            }

            VerticalDivider(thickness = 1.dp)

            Column(modifier = Modifier
                .padding(20.dp), Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = "Last Watered",
                    fontSize = 12.sp)
                Text(text = lastW,
                    fontSize = 10.sp)
            }
            VerticalDivider(thickness = 1.dp)
            Column(modifier = Modifier
                .padding(20.dp),
                Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = "Days until water",
                    fontSize = 12.sp)
                val waterInDays = when{
                    waterIn > 0 -> "Water in $waterIn days"
                    waterIn == 0L -> "Water today"
                    else -> "Watering overdue"
                }

                val markColor = when {
                    waterIn > 0 -> Color(0xFF2E7D32)
                    waterIn == 0L -> Color(0xFFE8A22B)
                    else -> Color(0xFFD32F2F)
                }
                Text(text = waterInDays,
                    fontSize = 10.sp,
                    color = markColor)
            }

        }
    }
}



@Composable
fun CollapsableImageHeader(imagePath : String?) {
    Box(modifier = Modifier.fillMaxWidth()
        .height(320.dp)){
        AsyncImage(
            model = imagePath?: R.drawable.plant,
            contentDescription = "PlantPic",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()

        )
        Box(modifier = Modifier
            .matchParentSize()
            .background(
                Brush.verticalGradient(colors =
                    listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.6f)
                    )
                ))
        )

    }


}

@Composable
fun CircularProgressWithText(value: Int) {

    Box(contentAlignment = Alignment.Center) {
        AnimatedProgress(value/100f)

        Column {
            Text(text = "${value}%",
                fontSize = 12.sp)
            Icon(Icons.Default.WaterDrop, contentDescription = "moisture",
                modifier = Modifier.size(15.dp),
                tint = Color(0xFF2196F3)
            )

        }

    }

}

fun epochToDate(epoch: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return LocalDate.ofEpochDay(epoch).format(formatter)
}

fun daysUntilWater(lastWateredEpoch: Long, frequency: Int): Long {
    val lastWateredDate = epochToLocalDate(lastWateredEpoch)
    val nextWaterDate = lastWateredDate.plusDays(frequency.toLong())
    return ChronoUnit.DAYS.between(LocalDate.now(), nextWaterDate)
}

@Composable
fun AnimatedProgress(progressTarget: Float) {

    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(150)
        progress.animateTo(
            progressTarget,
            animationSpec = tween(1000)
        )
    }

    CircularProgressIndicator(
        progress = { progress.value },
        modifier = Modifier.size(60.dp),
        color = Color(0xFF2196F3),
        strokeWidth = 5.dp,
        trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
        strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap
    )
}