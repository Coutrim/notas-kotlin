package br.com.anotaai.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.anotaai.R
import br.com.anotaai.model.Note
import br.com.anotaai.model.NoteViewModel

@Composable
fun NoteListScreen(noteViewModel: NoteViewModel, navController: NavHostController) {
    val notes by noteViewModel.notes.observeAsState(emptyList())
    val isLoading by noteViewModel.isLoading.observeAsState(true)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título no canto esquerdo superior
        Column(
            modifier = Modifier
                .padding(bottom = 20.dp)
        ) {
            Text(
                "Notas",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 20.dp)
            )
        }

        if (isLoading) {
            // Exibe o loading enquanto as notas estão sendo recuperadas
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else{

            if (notes.isEmpty()) {
                // Centralizando o conteúdo quando não há notas
                Column(
                    modifier = Modifier
                        .fillMaxSize() // Ocupar todo o espaço disponível
                        .align(Alignment.Center), // Centralizar os itens verticalmente e horizontalmente
                    horizontalAlignment = Alignment.CenterHorizontally, // Centralizar os itens horizontalmente
                    verticalArrangement = Arrangement.Center // Centralizar os itens verticalmente
                ) {
                    Text(
                        "Ops! Você ainda não cadastrou uma nota.",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(16.dp)) // Espaço entre o texto e a imagem

                    Image(
                        painter = painterResource(id = R.drawable.nenhumanotacadastrada), // Substitua pelo ID da sua imagem
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp), // Ajuste a altura conforme necessário
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = "Image by rawpixel.com on Freepik",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Light
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                }
            } else {
                LazyColumn {
                    items(notes) { note ->
                        NoteItem(
                            note = note,
                            onEdit = { noteId -> navController.navigate("edit_note/$noteId") },
                            onDelete = { noteId -> noteViewModel.deleteNote(noteId) },
                            onClick = { noteId -> navController.navigate("note_detail/$noteId") } // Navega para os detalhes
                        )
                    }
                }
            }
        }


        FloatingActionButton(
            onClick = { navController.navigate("add_note") },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Note")
        }
    }
}





@Composable
fun NoteItem(note: Note, onEdit: (Int) -> Unit, onDelete: (Int) -> Unit, onClick: (Int) -> Unit) {
    // Estado para controlar a exibição da modal de confirmação
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick(note.id) }, // Adiciona ação de clique no item
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = note.titulo,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espaço entre o título e os botões
                    Row {
                        IconButton(onClick = { onEdit(note.id) }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { showDeleteConfirmation = true }) { // Mostrar a modal de confirmação
                            Icon(Icons.Filled.Delete, contentDescription = "Delete")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = note.conteudo, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

    // Exibe a modal de confirmação quando `showDeleteConfirmation` é true
    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false }, // Fecha o diálogo se fora dele for clicado
            title = { Text("Confirmar exclusão") },
            text = { Text("Você tem certeza que deseja excluir esta nota?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete(note.id) // Executa a ação de exclusão
                        showDeleteConfirmation = false // Fecha o diálogo após a exclusão
                    }
                ) {
                    Text("Excluir")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteConfirmation = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

