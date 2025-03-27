package com.example.testappinformationbankcard.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testappinformationbankcard.ui.screen.CardInfoScreen
import com.example.testappinformationbankcard.ui.screen.MainViewModel
import com.example.testappinformationbankcard.ui.screen.SecondScreen
import com.example.testappinformationbankcard.ui.screen.SecondScreenViewModel

sealed class Screen(val route: String) {
    object Search : Screen("search")
    object History : Screen("history")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = navController.currentDestination?.route == Screen.Search.route,
                    onClick = { 
                        navController.navigate(Screen.Search.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Поиск") },
                    label = { Text("Поиск") }
                )
                NavigationBarItem(
                    selected = navController.currentDestination?.route == Screen.History.route,
                    onClick = { 
                        navController.navigate(Screen.History.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Star, contentDescription = "История") },
                    label = { Text("История") }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Search.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Search.route) {
                val viewModel: MainViewModel = hiltViewModel()
                CardInfoScreen(viewModel)
            }
            composable(Screen.History.route) {
                val viewModel: SecondScreenViewModel = hiltViewModel()
                SecondScreen(viewModel)
            }
        }
    }
} 