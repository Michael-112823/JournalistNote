package com.example.journalistnote.View.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.example.journalistnote.View.screens.CaseDetailScreen
import com.example.journalistnote.View.screens.CaseFormScreen
import com.example.journalistnote.View.screens.CaseListScreen
import com.example.journalistnote.View.screens.HomeScreen
import com.example.journalistnote.View.screens.InterviewFormScreen

/**
 * Define la navegación de la aplicación.
 *
 * Responsabilidad:
 * - Centralizar todas las rutas de navegación.
 * - Definir las transiciones entre pantallas.
 * - Proporcionar los controladores a cada pantalla.
 *
 * Rutas definidas:
 * - home: Pantalla principal / Dashboard
 * - cases: Listado de casos
 * - case/{id}: Detalle de un caso específico
 * - case/create: Formulario para crear un caso nuevo
 * - case/edit/{id}: Formulario para editar un caso existente
 * - case/{id}/interview/create: Formulario para crear una entrevista
 *
 * @param navController Controlador de navegación de Compose.
 * @param caseController Controlador de casos.
 * @param interviewController Controlador de entrevistas.
 * @param innerPadding Padding del Scaffold que se aplica al contenido.
 */
