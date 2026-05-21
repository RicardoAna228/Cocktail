package com.example.appcocktails.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appcocktails.ui.screens.detail.DetailScreen
import com.example.appcocktails.ui.screens.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen(onCocktailClick = { id ->
                navController.navigate("detail/$id")
            })
        }

        composable(
            route = "detail/{cocktailId}",
            arguments = listOf(navArgument("cocktailId") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("cocktailId") ?: return@composable
            DetailScreen(
                cocktailId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}