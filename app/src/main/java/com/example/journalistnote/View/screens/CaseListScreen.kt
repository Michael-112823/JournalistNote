package com.example.journalistnote.View.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.journalistnote.Controller.CaseController
import com.example.journalistnote.View.components.CaseCard
import com.example.journalistnote.View.components.SearchBar
// clase para mostrar y buscar los casos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseListScreen(
    navController: NavController,
    caseController: CaseController
) {
    var query by remember { mutableStateOf("") } //usamos una variable para revisar el campo de casos
    val cases by if (query.isBlank()) {
        caseController.getCases().collectAsState(initial = emptyList()) //sacamos todas las clases
    } else {
        caseController.searchCases(query).collectAsState(initial = emptyList()) //buscamos por el titulo que scriba el usuario


    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis casos") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("case/create") }
            ) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                modifier = Modifier.padding(12.dp)
            )

            if (cases.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay casos registrados")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp)
                ) {
                    items(items = cases, key = { it.id }) { case ->
                        CaseCard(
                            case = case,
                            onClick = {
                                navController.navigate("case/${case.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}


