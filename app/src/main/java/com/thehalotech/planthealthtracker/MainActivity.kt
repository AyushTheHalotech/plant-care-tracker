package com.thehalotech.planthealthtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.thehalotech.planthealthtracker.application.PlantApp
import com.thehalotech.planthealthtracker.navigation.Routes
import com.thehalotech.planthealthtracker.ui.screens.addplants.AddPlantViewModel
import com.thehalotech.planthealthtracker.ui.screens.addplants.AddPlantViewModelFactory
import com.thehalotech.planthealthtracker.ui.screens.addplants.AddPlantsScreen
import com.thehalotech.planthealthtracker.ui.screens.dashboard.DashboardScreen
import com.thehalotech.planthealthtracker.ui.screens.dashboard.DashboardViewModel
import com.thehalotech.planthealthtracker.ui.screens.dashboard.DashboardViewModelFactory
import com.thehalotech.planthealthtracker.ui.screens.plantdetails.DetailsScreen
import com.thehalotech.planthealthtracker.ui.screens.plantdetails.PlantDetailsViewModel
import com.thehalotech.planthealthtracker.ui.screens.plantdetails.PlantDetailsViewModelFactory
import com.thehalotech.planthealthtracker.ui.screens.plantlist.MyPlants
import com.thehalotech.planthealthtracker.ui.screens.plantlist.PlantListViewModelFactory
import com.thehalotech.planthealthtracker.ui.screens.plantlist.PlantlistViewModel
import com.thehalotech.planthealthtracker.ui.theme.PlantHealthTrackerTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlantHealthTrackerTheme {
                val controller = rememberNavController()
                val context = LocalContext.current
                val container = (context.applicationContext as PlantApp).container

                val viewModel: AddPlantViewModel = viewModel(factory = AddPlantViewModelFactory(container.plantApi, container.plantRepository))
                val plantListModel: PlantlistViewModel = viewModel(factory = PlantListViewModelFactory(container.plantRepository))
                val dashboardViewModel: DashboardViewModel = viewModel(factory = DashboardViewModelFactory(
                    container.plantRepository
                )
                )


                NavHost(
                    navController = controller,
                    startDestination = Routes.DASHBOARD,
                    enterTransition = {
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing
                            ))
                    },
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing
                            ))
                    }
                ) {
                    composable(Routes.DASHBOARD) {
                        DashboardScreen(controller, dashboardViewModel)
                    }
                    composable(Routes.MY_PLANTS) {
                        MyPlants(controller, plantListModel)
                    }
                    composable(Routes.ADD_PLANTS) {
                        AddPlantsScreen(controller, viewModel)
                    }
                    composable("details/{plantName}") { backStackEntry ->
                        val plantName = backStackEntry.arguments?.getString("plantName")!!
                        val plantDetailsViewModel: PlantDetailsViewModel = viewModel(factory = PlantDetailsViewModelFactory(container.plantRepository, plantName))
                        DetailsScreen(plantDetailsViewModel, controller)
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