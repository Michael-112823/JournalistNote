package com.example.journalistnote.View.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

//clase que representa a la barra de busqueda
@Composable
fun SearchBar(
    query: String, //valor del ultimo texto
    onQueryChange: (String) -> Unit, //identifica cuando se ingresa o cambia una letra
    modifier: Modifier = Modifier
) {


    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Buscar por título...") },
        leadingIcon = {Text("🔍")},
        singleLine = true, //mantiene su tamaño sin importar la longitud del texto
        modifier = modifier.fillMaxWidth()
    )

}
