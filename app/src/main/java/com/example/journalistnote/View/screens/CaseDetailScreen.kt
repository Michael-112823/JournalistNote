package com.example.journalistnote.View.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Pantalla que muestra el detalle completo de un caso.
 *
 * Responsabilidades:
 * - Mostrar toda la información del caso (título, descripción, fecha, estado, conclusión).
 * - Mostrar las entrevistas asociadas al caso.
 * - Permitir editar el caso.
 * - Permitir eliminar el caso con confirmación.
 * - Permitir agregar una nueva entrevista.
 *
 * @param caseId Identificador del caso a mostrar.
 * @param caseController Controlador para operaciones con casos.
 * @param interviewController Controlador para operaciones con entrevistas.
 * @param onEditClick Función para navegar a la edición del caso.
 * @param onAddInterviewClick Función para navegar a la creación de entrevista.
 * @param onNavigateBack Función para regresar a la pantalla anterior.
 */

