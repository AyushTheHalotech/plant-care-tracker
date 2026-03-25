package com.thehalotech.planthealthtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.thehalotech.planthealthtracker.application.PlantApp
import com.thehalotech.planthealthtracker.presentation.navigation.Routes
import com.thehalotech.planthealthtracker.presentation.addplant.AddPlantViewModel
import com.thehalotech.planthealthtracker.presentation.addplant.AddPlantViewModelFactory
import com.thehalotech.planthealthtracker.presentation.addplant.AddPlantsScreen
import com.thehalotech.planthealthtracker.presentation.dashboard.DashboardScreen
import com.thehalotech.planthealthtracker.presentation.dashboard.DashboardViewModel
import com.thehalotech.planthealthtracker.presentation.dashboard.DashboardViewModelFactory
import com.thehalotech.planthealthtracker.presentation.introduction.IntroductionScreen
import com.thehalotech.planthealthtracker.presentation.plantdetails.DetailsScreen
import com.thehalotech.planthealthtracker.presentation.plantdetails.PlantDetailsViewModel
import com.thehalotech.planthealthtracker.presentation.plantdetails.PlantDetailsViewModelFactory
import com.thehalotech.planthealthtracker.presentation.plantlist.MyPlants
import com.thehalotech.planthealthtracker.presentation.plantlist.PlantListViewModel
import com.thehalotech.planthealthtracker.presentation.plantlist.PlantListViewModelFactory
import com.thehalotech.planthealthtracker.presentation.theme.PlantHealthTrackerTheme
import com.thehalotech.planthealthtracker.presentation.user.UserRegistration
import com.thehalotech.planthealthtracker.presentation.user.UserViewModel
import com.thehalotech.planthealthtracker.presentation.user.UserViewModelFactory

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
                val mainViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(container.observeOnboardingUseCase))
                val isCompleted by mainViewModel.isOnboardingCompleted.collectAsState(initial = null)

                val viewModel: AddPlantViewModel = viewModel(factory = AddPlantViewModelFactory(container.searchPlantsUseCase, container.getPlantDetailsUseCase, container.addPlantUseCase))
                val plantListModel: PlantListViewModel = viewModel(factory = PlantListViewModelFactory(container.getPlantsUseCase, container.markPlantWatered))
                val dashboardViewModel: DashboardViewModel = viewModel(factory = DashboardViewModelFactory(
                    container.getPlantsUseCase, container.getUser
                )
                )
                val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(
                    container.getUser,
                    container.saveUser
                )
                )


                var startDestination = Routes.INTRODUCTION

                startDestination = if (isCompleted == null) {
                    Routes.INTRODUCTION
                } else {
                    if (isCompleted == true) {
                        Routes.DASHBOARD
                    } else {
                        Routes.INTRODUCTION
                    }
                }


                NavHost(
                    navController = controller,
                    startDestination = startDestination,
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
                    composable("details/{plantId}") { backStackEntry ->
                        val plantId = backStackEntry.arguments?.getString("plantId")!!
                        val plantDetailsViewModel: PlantDetailsViewModel = viewModel(factory = PlantDetailsViewModelFactory(container.getPlantByIdUseCase, container.markPlantWatered, plantId))
                        DetailsScreen(plantDetailsViewModel, controller)
                    }
                    composable(Routes.INTRODUCTION) {
                        IntroductionScreen(controller)
                    }

                    composable(Routes.USER_REGISTER) {
                        UserRegistration(userViewModel, controller)
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