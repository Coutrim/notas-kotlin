package br.com.anotaai.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.anotaai.dao.NoteDao
import br.com.anotaai.model.Note


// Define o banco de dados com as entidades e a versão
@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Função que retorna a instância do banco de dados
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "note_database"
                )
                    .fallbackToDestructiveMigration()  // Use fallback destrutivo ou migração aqui
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
