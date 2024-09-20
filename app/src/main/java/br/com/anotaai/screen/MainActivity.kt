package br.com.anotaai.screen

import EditNoteScreen
import NoteDetailScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import br.com.anotaai.database.AppDatabase
import br.com.anotaai.model.Note
import br.com.anotaai.model.NoteViewModel
import br.com.anotaai.model.NoteViewModelFactory
import br.com.anotaai.notification.worker.scheduleDailyNotification
import br.com.anotaai.repository.NoteRepository
import br.com.anotaai.ui.theme.AnotaAiTheme


class MainActivity : ComponentActivity() {
    private lateinit var noteViewModel: NoteViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = NoteRepository(database.noteDao())
        noteViewModel = ViewModelProvider(this, NoteViewModelFactory(repository))
            .get(NoteViewModel::class.java)

        setContent {
            AnotaAiTheme {
                MainScreen(noteViewModel)
            }
        }

        scheduleDailyNotification(this)
    }
}

@Composable
fun MainScreen(noteViewModel: NoteViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list_notes") {
        composable("list_notes") {
            NoteListScreen(noteViewModel, navController)
        }
        composable("add_note") {
            AddNoteScreen(noteViewModel, navController)
        }
        composable("edit_note/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() ?: 0
            EditNoteScreen(noteViewModel, navController, noteId)
        }
        composable("note_detail/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull() ?: 0
            NoteDetailScreen(noteViewModel, navController, noteId)
        }
    }
}
