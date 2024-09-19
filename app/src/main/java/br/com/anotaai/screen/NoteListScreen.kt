package br.com.anotaai.screen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.anotaai.model.Note
import br.com.anotaai.model.NoteViewModel


@Composable
fun NoteListScreen(noteViewModel: NoteViewModel, navController: NavHostController) {
    val notes by noteViewModel.notes.observeAsState(emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (notes.isEmpty()) {
            Text("Nenhuma nota cadastrada.", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.align(
                Alignment.Center))
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
                Text(text = "${note.titulo}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = " ${note.conteudo}", style = MaterialTheme.typography.bodyMedium)
            }
            Row {
                IconButton(onClick = { onEdit(note.id) }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { onDelete(note.id) }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}
