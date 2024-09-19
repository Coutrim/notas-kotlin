package br.com.anotaai.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.anotaai.model.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(noteViewModel: NoteViewModel, navController: NavHostController, noteId: Int) {
    val note by noteViewModel.selectedNote.observeAsState()
    var name by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    var statusError by remember { mutableStateOf(false) }

    // Recarregar dados da nota quando o noteId mudar
    LaunchedEffect(noteId) {
        noteViewModel.loadNoteById(noteId)
    }

    // Atualizar os campos de texto quando a nota for carregada
    LaunchedEffect(note) {
        note?.let {
            name = it.name
            status = it.status
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TopAppBar(
            title = { Text("Edit Note") },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                if (it.length <= 255) name = it
            },
            label = { Text("Note Name") },
            isError = nameError,
            placeholder = { Text("Enter note name") }
        )
        if (nameError) {
            Text("Note Name is required and should be up to 255 characters.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = status,
            onValueChange = {
                if (it.length <= 255) status = it
            },
            label = { Text("Status") },
            isError = statusError,
            placeholder = { Text("Enter status") }
        )
        if (statusError) {
            Text("Status is required and should be up to 255 characters.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            nameError = name.isBlank()
            statusError = status.isBlank()

            if (!nameError && !statusError) {
                noteViewModel.updateNote(note!!.copy(name = name, status = status))
                navController.navigate("list_notes")
            }
        }) {
            Text("Update Note")
        }
    }
}
