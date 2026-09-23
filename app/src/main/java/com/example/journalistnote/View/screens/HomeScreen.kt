package com.example.journalistnote.View.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.journalistnote.Controller.CaseController
import com.example.journalistnote.Model.CaseStatus
import androidx.compose.material3.ExperimentalMaterial3Api
// pantalla principal del proyecto que muestra un conteo de casos, solo temporal
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    caseController: CaseController
) {
    val total by caseController.getTotalCases().collectAsState(initial = 0)
    val abiertos by caseController.getCasesCountByStatus(CaseStatus.OPEN)
        .collectAsState(initial = 0)
    val enInvestigacion by caseController.getCasesCountByStatus(CaseStatus.IN_PROGRESS)
        .collectAsState(initial = 0)
    val cerrados by caseController.getCasesCountByStatus(CaseStatus.CLOSED)
        .collectAsState(initial = 0)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("JournalistNote") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                "Resumen general",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                DashboardCard(label = "Total", value = total, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                DashboardCard(label = "Abiertos", value = abiertos, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                DashboardCard(
                    label = "En investigación",
                    value = enInvestigacion,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                DashboardCard(label = "Cerrados", value = cerrados, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("cases") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver todos los casos")
            }
        }
    }
}

// funcion auxiliar para la creacion del card
@Composable
private fun DashboardCard(
    label: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value.toString(), style = MaterialTheme.typography.headlineMedium)
            Text(label, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

