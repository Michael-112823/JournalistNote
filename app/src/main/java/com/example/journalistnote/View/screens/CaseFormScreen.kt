package com.example.journalistnote.View.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.journalistnote.Controller.CaseController
import com.example.journalistnote.Model.CaseStatus
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseFormScreen(
    navController: NavController,
    caseController: CaseController,
    caseId: Long? = null
) {
    // ---------- 1. Estado de cada campo del formulario ----------
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(CaseStatus.OPEN) }
    var conclusion by remember { mutableStateOf("") }

    var initialized by remember { mutableStateOf(false) }
    var titleError by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // ---------- 2. Si es edición, observamos el caso existente ----------
    // (declarado FUERA de cualquier `if`, para poder usarlo también en el botón Guardar)
    val caseState = if (caseId != null) {
        val flowState = caseController.getCaseById(caseId).collectAsState(initial = null)
        flowState.value
    } else {
        null
    }

    // ---------- 3. Precargamos los campos una sola vez ----------
    LaunchedEffect(caseState) {
        if (caseState != null && !initialized) {
            title = caseState.title
            description = caseState.description
            date = caseState.date
            status = caseState.status
            conclusion = caseState.conclusion
            initialized = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (caseId == null) "Nuevo caso" else "Editar caso") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // ---------- 4. Campo: título ----------
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    titleError = false
                },
                label = { Text("Título") },
                isError = titleError,
                modifier = Modifier.fillMaxWidth()
            )
            if (titleError) {
                Text(
                    text = "El título es obligatorio",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ---------- 5. Campo: descripción ----------
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ---------- 6. Campo: fecha ----------
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Fecha (AAAA-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ---------- 7. Campo: estado (dropdown) ----------
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = status.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Estado") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    CaseStatus.entries.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.name) },
                            onClick = {
                                status = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ---------- 8. Campo: conclusión ----------
            OutlinedTextField(
                value = conclusion,
                onValueChange = { conclusion = it },
                label = { Text("Conclusión") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ---------- 9. Botón Guardar ----------
            Button(
                onClick = {
                    if (title.isBlank()) {
                        titleError = true
                        return@Button
                    }
                    coroutineScope.launch {
                        if (caseId == null) {
                            // Modo creación: se envían los campos sueltos
                            caseController.createCase(
                                title = title,
                                description = description,
                                date = date,
                                status = status,
                                conclusion = conclusion
                            )
                        } else if (caseState != null) {
                            // Modo edición: se envía el objeto completo, con su id real
                            caseController.updateCase(
                                caseState.copy(
                                    title = title,
                                    description = description,
                                    date = date,
                                    status = status,
                                    conclusion = conclusion
                                )
                            )
                        }
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}