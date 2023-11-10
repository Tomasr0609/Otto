package com.example.tpfinal

// Importaciones necesarias para trabajar con la base de datos Room y otras clases esenciales
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    @ColumnInfo(name = "dateAdded")
    val dateAdded: Date, // Propiedad que almacena la fecha de adición de la nota
    @ColumnInfo(name = "noteText")
    val noteText: String, // Propiedad que almacena el texto de la nota

)