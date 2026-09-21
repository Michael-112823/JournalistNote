package com.example.journalistnote.View.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Pantalla que muestra el listado de casos registrados.
 *
 * Responsabilidades:
 * - Mostrar todos los casos en un listado.
 * - Permitir buscar casos por título.
 * - Permitir acceder al detalle de cada caso.
 * - Permitir navegar a la pantalla de creación de caso.
 *
 * La búsqueda se realiza en tiempo real: cada cambio
 * en el campo de texto actualiza el listado.
 *
 * @param caseController Controlador para acceder a los datos de casos.
 * @param onCaseClick Función que se ejecuta al seleccionar un caso.
 * @param onAddCaseClick Función que se ejecuta para crear un nuevo caso.
 */
