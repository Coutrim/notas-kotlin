package br.com.anotaai.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.anotaai.model.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(noteViewModel: NoteViewModel, navController: NavHostController, noteId: Int) {
    // Observar a nota selecionada
    val note by noteViewModel.selectedNote.observeAsState()

    // Carregar a nota ao abrir a tela
    LaunchedEffect(noteId) {
        noteViewModel.loadNoteById(noteId)
    }

    // Scaffold para a tela
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Note Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Box para layout da nota ou carregando
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center // Centraliza o conteúdo
        ) {
            // Se a nota foi carregada, mostrar os detalhes
            note?.let {
                Column {
                    Text(text = "${it.titulo}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "${it.conteudo}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.navigate("edit_note/${it.id}") }) {
                        Text("Edit Note")
                    }
                }
            } ?: run {
                // Se ainda estiver carregando, mostrar texto de carregamento
                Text("Loading...", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
