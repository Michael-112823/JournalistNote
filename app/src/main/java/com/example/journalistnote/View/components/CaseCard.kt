package com.example.journalistnote.View.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


/**
 * Componente reutilizable que muestra la información
 * resumida de un caso criminal en una tarjeta.
 *
 * Responsabilidades:
 * - Mostrar título, fecha y estado de un caso.
 * - Resaltar visualmente el estado según su valor.
 * - Permitir hacer clic para navegar al detalle del caso.
 *
 * @param case El caso a mostrar.
 * @param onClick Función que se ejecuta al hacer clic en la tarjeta.
 */
