package com.example.tpfinal

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    // Interfaz DAO que define métodos para interactuar con la base de datos para la entidad Note
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)
    // Método para insertar una nueva nota en la base de datos

    @Query("SELECT * FROM notes ORDER BY dateAdded DESC")
    fun getNotes(): Flow<List<Note>>
    // Método para obtener todas las notas de la base de datos
    // Devuelve un objeto Flow<List<Note>> que permite observar los cambios en la lista de notas de manera automática
    // La consulta SQL ordena las notas por fecha de adición en orden descendente


    @Update
    suspend fun updateNote(note: Note)
    // Método para actualizar una nota existente de la base de datos


    @Delete
    suspend fun deleteNote(note: Note)
    // Método para eliminar una nota de la base de datos
}

//Este archivo DAO nos da métodos utilizados para crear, modificar, y eliminar notas