package com.example.journalistnote.View.navigation

import androidx.compose.runtime.Composable
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
import com.example.journalistnote.View.screens.InterviewFormScreen
// Esta clase es la que crea la navegacion entre los diferentes screens
@Composable
fun AppNavigation(
    navController: NavHostController,
    caseController: CaseController,
    interviewController: InterviewController
) {
    NavHost(
        navController = navController,
        startDestination = "home" // para abrir siempre en el dashboard
    ) {
        // cuando se llame el navegador mostrara el composable correspondiente
        /*
        composable("home") {
            HomeScreen(
                navController = navController,
                caseController = caseController
            )
        }*/
        // el listado de casos
        composable("cases") { //
            CaseListScreen(
                navController = navController,
                caseController = caseController
            )
        }

        // cuando se llame al formulario de caso se envia de una vez el ID vacio
        // para iniciar con la creacion
        composable("case/create") {
            CaseFormScreen(
                navController = navController,
                caseController = caseController,
                caseId = null
            )
        }


        // composable con parametros IA

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
