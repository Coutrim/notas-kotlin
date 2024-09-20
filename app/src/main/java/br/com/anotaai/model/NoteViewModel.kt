package br.com.anotaai.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.anotaai.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes

    private val _selectedNote = MutableLiveData<Note?>()
    val selectedNote: LiveData<Note?> get() = _selectedNote

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        loadNotes()
    }

    fun saveNote(titulo: String, conteudo: String) {
        viewModelScope.launch {
            val note = Note(titulo = titulo, conteudo = conteudo)
            repository.insert(note)
            loadNotes()
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.update(note)
            loadNotes()
        }
    }

    fun loadNoteById(id: Int) {
        viewModelScope.launch {
            _selectedNote.value = repository.getNoteById(id)
        }
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _isLoading.value = true
            _notes.value = repository.getAllNotes()
            _isLoading.value = false
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
            loadNotes()
        }
    }
}
