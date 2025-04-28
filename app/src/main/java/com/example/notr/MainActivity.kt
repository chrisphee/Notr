package com.example.notr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.notr.ui.NotrNavHost
import com.example.notr.ui.NotrScreen
import com.example.notr.ui.storage.StorageScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                NotrApp()
            }
        }
    }
}

@Composable
fun NotrApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = { NotrBottomBar(navController = navController) }
    ) { innerPadding ->
        // The NavHost File function
        NotrNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun NotrBottomBar(navController: NavController, modifier: Modifier = Modifier) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(modifier = modifier) {
        // Home item
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Create, contentDescription = "Home") },
            label = { Text("Create Note") },
            selected = currentDestination?.hierarchy?.any { it.route == NotrScreen.Home.name } == true,
            // TODO: DON'T HARDCODE THIS, FIX THIS UP BRO
            onClick = {
                navController.navigate(NotrScreen.Home.name) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                popUpTo(NotrScreen.Home.name) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
                }
            }
        )

        // Storage item
        NavigationBarItem(
            icon = { Icon(Icons.Filled.List, contentDescription = "Storage") },
            label = { Text("My Notes") },
            selected = currentDestination?.hierarchy?.any { it.route == NotrScreen.Storage.name } == true,
            // TODO: DON'T HARDCODE THIS, FIX THIS UP BRO
            onClick = {
                navController.navigate(NotrScreen.Storage.name) {
                popUpTo(NotrScreen.Home.name) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
                }
            }
        )
    }
}
