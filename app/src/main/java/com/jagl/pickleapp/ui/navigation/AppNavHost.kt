package com.jagl.pickleapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jagl.pickleapp.features.detail.CharacterDetailScreen
import com.jagl.pickleapp.features.detail.DetailViewModel
import com.jagl.pickleapp.features.episode_detail.EpisodeDetailScreen
import com.jagl.pickleapp.features.episode_detail.EpisodeDetailViewModel
import com.jagl.pickleapp.features.home.HomeScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToDetail = { id ->
                    navController.navigate("episode_detail/$id")
                }
            )
        }
        composable(
            "episode_detail/{${EpisodeDetailViewModel.EPISODE_ID}}",
            arguments = listOf(navArgument(EpisodeDetailViewModel.EPISODE_ID) {
                type = NavType.LongType
            })
        ) {
            EpisodeDetailScreen(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateToCharacterDetail = { characterId ->
                    navController.navigate("character_detail/$characterId")
                }
            )
        }
        composable(
            "character_detail/{${DetailViewModel.CHARACTER_ID}}",
            arguments = listOf(navArgument(DetailViewModel.CHARACTER_ID) {
                type = NavType.LongType
            })
        ) {
            CharacterDetailScreen(onBack = { navController.popBackStack() })
        }
    }
}