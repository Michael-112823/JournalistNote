package com.example.journalistnote.View.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.journalistnote.Controller.CaseController
import com.example.journalistnote.Controller.InterviewController
import com.example.journalistnote.View.screens.CaseDetailScreen
import com.example.journalistnote.View.screens.CaseFormScreen
import com.example.journalistnote.View.screens.CaseListScreen
import com.example.journalistnote.View.screens.HomeScreen
import com.example.journalistnote.View.screens.InterviewFormScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    caseController: CaseController,
    interviewController: InterviewController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
                navController = navController,
                caseController = caseController
            )
        }

        composable("cases") {
            CaseListScreen(
                navController = navController,
                caseController = caseController
            )
        }

        composable("case/create") {
            CaseFormScreen(
                navController = navController,
                caseController = caseController,
                caseId = null
            )
        }

        composable(
            route = "case/{caseId}",
            arguments = listOf(navArgument("caseId") { type = NavType.LongType })
        ) { backStackEntry ->
            val caseId = backStackEntry.arguments?.getLong("caseId") ?: 0L
            CaseDetailScreen(
                navController = navController,
                caseController = caseController,
                interviewController = interviewController,
                caseId = caseId
            )
        }

        composable(
            route = "case/edit/{caseId}",
            arguments = listOf(navArgument("caseId") { type = NavType.LongType })
        ) { backStackEntry ->
            val caseId = backStackEntry.arguments?.getLong("caseId") ?: 0L
            CaseFormScreen(
                navController = navController,
                caseController = caseController,
                caseId = caseId
            )
        }

        composable(
            route = "case/{caseId}/interview/create",
            arguments = listOf(navArgument("caseId") { type = NavType.LongType })
        ) { backStackEntry ->
            val caseId = backStackEntry.arguments?.getLong("caseId") ?: 0L
            InterviewFormScreen(
                navController = navController,
                interviewController = interviewController,
                caseId = caseId
            )
        }
    }
}
