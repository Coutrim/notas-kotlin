import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
                title = { Text("Detalhes da nota") },
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
                .padding(16.dp)
        ) {
            // Se a nota foi carregada, mostrar os detalhes
            note?.let {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Linha com o título e o botão de editar ao lado
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Título da nota maior e em negrito
                        Text(
                            text = it.titulo,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.weight(1f) // Faz o título ocupar o máximo de espaço possível
                        )
                        // Botão de editar
                        IconButton(onClick = { navController.navigate("edit_note/${it.id}") }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit Note")
                        }
                    }
                    // Conteúdo da nota
                    Text(
                        text = it.conteudo,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            } ?: run {
                // Se ainda estiver carregando, mostrar texto de carregamento
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Carregando...", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
