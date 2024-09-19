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
    var name by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    var statusError by remember { mutableStateOf(false) }

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
                noteViewModel.saveNote(name, status)
                navController.navigate("list_notes") // Navegar para a lista de notas
            }
        }) {
            Text("Save Note")
        }
    }
}