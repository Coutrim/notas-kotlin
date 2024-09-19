package br.com.anotaai.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.anotaai.model.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM note_table WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Query("SELECT * FROM note_table")
    suspend fun getAllNotes(): List<Note>

    @Query("DELETE FROM note_table WHERE id = :id")
    suspend fun deleteById(id: Int)
}
