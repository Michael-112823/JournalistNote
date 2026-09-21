package com.example.journalistnote.View.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Pantalla que permite crear o editar un caso criminal.
 *
 * Responsabilidades:
 * - Mostrar un formulario con los campos del caso.
 * - Validar que los campos obligatorios no estén vacíos.
 * - Permitir seleccionar el estado del caso.
 * - Guardar un caso nuevo o actualizar uno existente.
 * - Mostrar mensajes de error al usuario.
 *
 * Esta pantalla se reutiliza tanto para crear como para editar.
 * Si [caseId] es null, se crea un caso nuevo.
 * Si [caseId] tiene un valor, se carga el caso existente para editarlo.
 *
 * @param caseId Identificador del caso a editar, o null para crear uno nuevo.
 * @param caseController Controlador para operaciones con casos.
 * @param onNavigateBack Función para regresar a la pantalla anterior.
 */
