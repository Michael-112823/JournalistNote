package com.example.journalistnote.View.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.journalistnote.Controller.InterviewController
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewFormScreen(
    navController: NavController,
    interviewController: InterviewController,
    caseId: Long
) {
    var interviewee by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var findings by remember { mutableStateOf("") }

    var intervieweeError by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val datePickerState = rememberDatePickerState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva entrevista", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text("Volver")
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
            OutlinedTextField(
                value = interviewee,
                onValueChange = {
                    interviewee = it
                    intervieweeError = false
                },
                label = { Text("Persona entrevistada *") },
                isError = intervieweeError,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            if (intervieweeError) {
                Text(
                    "Este campo es obligatorio",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = date,
                onValueChange = {},
                readOnly = true,
                label = { Text("Fecha") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                shape = RoundedCornerShape(12.dp),
                enabled = false
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notas") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = findings,
                onValueChange = { findings = it },
                label = { Text("Hallazgos") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 2
            )

            Spacer(modifier = Modifier.height(24.dp))

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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Guardar", fontSize = 16.sp)
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        date = sdf.format(Date(millis))
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
