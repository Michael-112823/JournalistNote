package com.example.journalistnote.View.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.example.journalistnote.Controller.InterviewController
import com.example.journalistnote.Model.CaseStatus
import kotlinx.coroutines.launch

// pantalla que muestra la informaicon de un caso, ademas de permitir eliminarlo

// se llega a usar en el caseDetailScreen
private fun statusLabel(status: CaseStatus): String {
    return when (status) {
        CaseStatus.OPEN -> "Abierto"
        CaseStatus.IN_PROGRESS -> "En investigación"
        CaseStatus.CLOSED -> "Cerrado"
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseDetailScreen(
    navController: NavController,
    caseController: CaseController,
    interviewController: InterviewController,
    caseId: Long
) {
    // ---------- 1. Observamos el caso y sus entrevistas ----------
    val case by caseController.getCaseById(caseId).collectAsState(initial = null)
    val interviews by interviewController.getInterviewsByCaseId(caseId)
        .collectAsState(initial = emptyList())

    var showDeleteDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // ---------- 2. Estructura con barra superior (volver, editar, eliminar) ----------
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del caso") },
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("‹ Volver")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        navController.navigate("case/edit/$caseId")
                    }) {
                        Text("Editar")
                    }
                    TextButton(onClick = { showDeleteDialog = true }) {
                        Text("Eliminar")
                    }
                }
            )
        }
    ) { innerPadding ->

        // ---------- 3. Cuerpo: mientras carga vs. ya con datos ----------
        if (case == null) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Text("Cargando...")
            }
        } else {
            val currentCase = case!!

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                // ---------- 4. Datos generales del caso ----------
                Text(currentCase.title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "${currentCase.date} · ${statusLabel(currentCase.status)}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text("Descripción", style = MaterialTheme.typography.titleSmall)
                Text(currentCase.description)

                Spacer(modifier = Modifier.height(12.dp))
                Text("Conclusión", style = MaterialTheme.typography.titleSmall)
                Text(
                    if (currentCase.conclusion.isBlank()) "Sin conclusión registrada"
                    else currentCase.conclusion
                )

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                // ---------- 5. Sección de entrevistas ----------
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        "Entrevistas (${interviews.size})",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = {
                        navController.navigate("case/$caseId/interview/create")
                    }) {
                        Text("+ Nueva entrevista")
                    }
                }

                if (interviews.isEmpty()) {
                    Text("Aún no hay entrevistas registradas")
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(items = interviews, key = { it.id }) { interview ->
                            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                Text(
                                    interview.interviewee,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    interview.date,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    interview.findings,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Divider()
                        }
                    }
                }
            }
        }
    }

    // ---------- 6. Diálogo de confirmación para eliminar el caso ----------
    if (showDeleteDialog && case != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar caso") },
            text = {
                Text("¿Seguro que quieres eliminar este caso? Esta acción no se puede deshacer.")
            },
            confirmButton = {
                TextButton(onClick = {
                    coroutineScope.launch {
                        caseController.deleteCase(case!!)
                        navController.popBackStack()
                    }
                    showDeleteDialog = false
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}