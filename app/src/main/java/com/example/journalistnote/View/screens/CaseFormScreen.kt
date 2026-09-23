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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.journalistnote.Controller.CaseController
import com.example.journalistnote.Model.CaseStatus
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseFormScreen(
    navController: NavController,
    caseController: CaseController,
    caseId: Long? = null
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(CaseStatus.OPEN) }
    var conclusion by remember { mutableStateOf("") }

    var initialized by remember { mutableStateOf(false) }
    var titleError by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val datePickerState = rememberDatePickerState()

    val caseState = if (caseId != null) {
        val flowState = caseController.getCaseById(caseId).collectAsState(initial = null)
        flowState.value
    } else {
        null
    }

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
                title = {
                    Text(
                        if (caseId == null) "Nuevo caso" else "Editar caso",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
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
                value = title,
                onValueChange = {
                    title = it
                    titleError = false
                },
                label = { Text("Titulo *") },
                isError = titleError,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            if (titleError) {
                Text(
                    text = "El titulo es obligatorio",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripcion") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 2
            )

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
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
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

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = conclusion,
                onValueChange = { conclusion = it },
                label = { Text("Conclusion") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 2
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (title.isBlank()) {
                        titleError = true
                        return@Button
                    }
                    coroutineScope.launch {
                        if (caseId == null) {
                            caseController.createCase(
                                title = title,
                                description = description,
                                date = date,
                                status = status,
                                conclusion = conclusion
                            )
                        } else if (caseState != null) {
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

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate("cases") },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Cancelar", fontSize = 16.sp)
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
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
