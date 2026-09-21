package com.example.journalistnote.View.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Pantalla principal / Dashboard de la aplicación.
 *
 * Responsabilidades:
 * - Mostrar un resumen general de los casos registrados.
 * - Mostrar el conteo total de casos.
 * - Mostrar el conteo de casos por estado (abierto, en investigación, cerrado).
 * - Proporcionar acceso rápido al listado de casos.
 *
 * Esta pantalla es el punto de entrada de la aplicación
 * después del lanzamiento.
 *
 * @param caseController Controlador para acceder a los datos de casos.
 */

