package com.example.journalistnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.journalistnote.Controller.CaseController
import com.example.journalistnote.Controller.InterviewController
import com.example.journalistnote.View.navigation.AppNavigation
import com.example.journalistnote.ui.theme.JournalistNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val caseController = CaseController(applicationContext)
        val interviewController = InterviewController(applicationContext)

        setContent {
            JournalistNoteTheme {
                MainScreen(caseController, interviewController)
            }
        }
    }
}

data class NavItem(val route: String, val label: String, val icon: @Composable () -> Unit)

@Composable
fun MainScreen(caseController: CaseController, interviewController: InterviewController) {
    val navController = rememberNavController()
    val items = listOf(
        NavItem("home", "Inicio") { Icon(Icons.Rounded.Home, contentDescription = "Inicio") },
        NavItem("cases", "Casos") { Icon(Icons.Rounded.List, contentDescription = "Casos") }
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    NavigationBarItem(
                        icon = item.icon,
                        label = { Text(item.label) },
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            caseController = caseController,
            interviewController = interviewController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}


