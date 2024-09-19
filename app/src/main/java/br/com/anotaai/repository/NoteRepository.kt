package br.com.anotaai.repository

import br.com.anotaai.dao.NoteDao
import br.com.anotaai.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun getAllNotes(): List<Note> {
        return noteDao.getAllNotes()
    }

    suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }

    suspend fun delete(id: Int) {
        noteDao.deleteById(id)
    }
}