package com.example.journalistnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.journalistnote.ui.theme.JournalistNoteTheme

/**
 * Actividad principal de la aplicación.
 *
 * Responsabilidad:
 * - Configurar el tema visual de la aplicación.
 * - Inicializar los controladores de datos.
 * - Configurar la barra de navegación inferior.
 * - Integrar el sistema de navegación de Compose.
 *
 * Esta actividad actúa como punto de entrada y orquesta
 * la presentación de las diferentes pantallas de la aplicación.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Se instancian los controladores que serán compartidos
        // entre todas las pantallas de la aplicación.


    }
}

/**
 * Pantalla principal con barra de navegación inferior.
 *
 * @param caseController Controlador de casos.
 * @param interviewController Controlador de entrevistas.
 */

