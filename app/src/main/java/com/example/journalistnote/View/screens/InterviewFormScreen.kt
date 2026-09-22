package com.example.journalistnote.View.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.journalistnote.Controller.InterviewController
import kotlinx.coroutines.launch
// clase que permite la creacion de una nueva entrevista

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewFormScreen(
    navController: NavController,
    interviewController: InterviewController,
    caseId: Long
) {
    // establecemos los campos del formulario como en el caseForm
    var interviewee by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var findings by remember { mutableStateOf("") }

    var intervieweeError by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva entrevista") },
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("‹ Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // campo del entrevistado
            OutlinedTextField(
                value = interviewee,
                onValueChange = {
                    interviewee = it
                    intervieweeError = false
                },
                label = { Text("Persona entrevistada") },
                isError = intervieweeError,
                modifier = Modifier.fillMaxWidth()
            )
        }
            if (intervieweeError) {
                Text(
                    "Este campo es obligatorio",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // campo de la fecha
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Fecha (AAAA-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // campo de las notas
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notas") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            //campo de los hallazgos
            OutlinedTextField(
                value = findings,
                onValueChange = { findings = it },
                label = { Text("Hallazgos") },
                modifier = Modifier.fillMaxWidth()
            )
        Spacer(modifier = Modifier.height(16.dp))

        // boton para guardar la infromacion
        Button(
            onClick = {
                if (interviewee.isBlank()) {
                    intervieweeError = true
                    return@Button
                }
                coroutineScope.launch {
                    interviewController.createInterview(
                        caseId = caseId,
                        interviewee = interviewee,
                        date = date,
                        notes = notes,
                        findings = findings
                    )
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}
