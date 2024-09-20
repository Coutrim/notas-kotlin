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
    var titulo by remember { mutableStateOf("") }
    var conteudo by remember { mutableStateOf("") }
    var tituloError by remember { mutableStateOf(false) }
    var conteudoError by remember { mutableStateOf(false) }


    LaunchedEffect(noteId) {
        noteViewModel.loadNoteById(noteId)
    }

    LaunchedEffect(note) {
        note?.let {
            titulo = it.titulo
            conteudo = it.conteudo
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar nota") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = titulo,
                onValueChange = {
                    if (it.length <= 60) titulo = it
                },
                label = { Text("Título") },
                isError = tituloError,
                placeholder = { Text("Digite o título da nota") },
                modifier = Modifier.fillMaxWidth()
            )
            if (tituloError) {
                Text(
                    "O título da nota é obrigatório e pode ter até 60 carateres.",
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
                label = { Text("Conteúdo") },
                isError = conteudoError,
                placeholder = { Text("Digite o conteúdo da nota") },
                modifier = Modifier.fillMaxWidth()
            )
            if (conteudoError) {
                Text(
                    "O conteúdo da nota é obrigatório e pode ter até 255 caracteres.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    tituloError = titulo.isBlank()
                    conteudoError = conteudo.isBlank()

                    if (!tituloError && !conteudoError) {
                        noteViewModel.updateNote(note!!.copy(titulo = titulo, conteudo = conteudo))
                        navController.navigate("list_notes")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Atualizar nota")
            }
        }
    }
}
