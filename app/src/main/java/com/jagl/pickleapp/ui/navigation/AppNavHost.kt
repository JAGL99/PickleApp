package com.jagl.pickleapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jagl.pickleapp.features.detail.DetailScreen
import com.jagl.pickleapp.features.detail.DetailViewModel
import com.jagl.pickleapp.features.home.HomeScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToDetail = { id ->
                    navController.navigate("detail/$id")
                }
            )
        }
        composable(
            "detail/{${DetailViewModel.CHARACTER_ID}}",
            arguments = listOf(navArgument(DetailViewModel.CHARACTER_ID) {
                type = NavType.LongType
            })
        ) {
            DetailScreen(onBack = { navController.popBackStack() })
        }
    }
}