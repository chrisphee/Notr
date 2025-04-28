package com.example.notr.ui
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notr.ui.home.HomeScreen
import com.example.notr.ui.storage.StorageScreen



/**
 * Enum class for the different screens in the app
 */
enum class NotrScreen {
    Home,
    Storage
}

/**
 * Navigation graph for the app
 */
@Composable
fun NotrNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NotrScreen.Home.name,
        modifier = modifier
    ) {
        composable(route = NotrScreen.Home.name) {
            HomeScreen()
        }
        composable(route = NotrScreen.Storage.name) {
            StorageScreen()
        }
    }
}

