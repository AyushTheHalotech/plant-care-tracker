package com.thehalotech.planthealthtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
import com.thehalotech.planthealthtracker.navigation.Routes
import com.thehalotech.planthealthtracker.ui.screens.addplants.AddPlantsScreen
import com.thehalotech.planthealthtracker.ui.screens.dashboard.DashboardScreen
import com.thehalotech.planthealthtracker.ui.screens.plantlist.MyPlants
import com.thehalotech.planthealthtracker.ui.theme.PlantHealthTrackerTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlantHealthTrackerTheme {
                val controller = rememberNavController()

                NavHost(
                    navController = controller,
                    startDestination = Routes.DASHBOARD
                ) {
                    composable(Routes.DASHBOARD) {
                        DashboardScreen(controller)
                    }
                    composable(Routes.MY_PLANTS) {
                        MyPlants(controller)
                    }
                    composable(Routes.ADD_PLANTS) {
                        AddPlantsScreen(controller)
                    }
                }
            }
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