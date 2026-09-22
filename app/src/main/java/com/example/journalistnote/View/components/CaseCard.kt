package com.example.journalistnote.View.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.journalistnote.Model.Case
import com.example.journalistnote.Model.CaseStatus
// clase que muestra la informacion de un caso en una tarjeta
@Composable
fun CaseCard(
    case: Case,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // practica recomendable
) {

    Card(
        onClick = onClick, // hace que toda la carta sea clickable
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) { //permite que los datos de tituo y fecha ocupen toda la columna
                Text(
                    text = case.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = case.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Surface(
                color = statusColor(case.status),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = statusLabel(case.status),
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }

}
// identificadores visuales de los estados del caso
private fun statusColor(status: CaseStatus): Color {
    return when (status) {
        CaseStatus.OPEN -> Color(0xFF2196F3)        // azul
        CaseStatus.IN_PROGRESS -> Color(0xFFFFA000)  // ámbar
        CaseStatus.CLOSED -> Color(0xFF757575)       // gris
    }
}

private fun statusLabel(status: CaseStatus): String {
    return when (status) {
        CaseStatus.OPEN -> "Abierto"
        CaseStatus.IN_PROGRESS -> "En investigación"
        CaseStatus.CLOSED -> "Cerrado"
    }
}
