package br.com.anotaai.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.anotaai.model.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(noteViewModel: NoteViewModel, navController: NavHostController) {
    var titulo by remember { mutableStateOf("") }
    var conteudo by remember { mutableStateOf("") }
    var tituloError by remember { mutableStateOf(false) }
    var conteudoError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TopAppBar(
            title = { Text("Add Note") },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = {
                if (it.length <= 255) titulo = it
            },
            label = { Text("Note Name") },
            isError = tituloError,
            placeholder = { Text("Enter note titulo") }
        )
        if (tituloError) {
            Text("Note Name is required and should be up to 255 characters.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = conteudo,
            onValueChange = {
                if (it.length <= 255) conteudo = it
            },
            label = { Text("Status") },
            isError = conteudoError,
            placeholder = { Text("Enter conteudo") }
        )
        if (conteudoError) {
            Text("Status is required and should be up to 255 characters.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            tituloError = titulo.isBlank()
            conteudoError = conteudo.isBlank()

            if (!tituloError && !conteudoError) {
                noteViewModel.saveNote(titulo, conteudo)
                navController.navigate("list_notes") // Navegar para a lista de notas
            }
        }) {
            Text("Save Note")
        }
    }
}