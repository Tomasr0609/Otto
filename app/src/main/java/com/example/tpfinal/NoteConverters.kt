package com.example.tpfinal

import androidx.room.TypeConverter
import java.util.*


class NoteConverters {
    // Clase que contiene funciones para convertir entre tipos de datos utilizados en la entidad "Note"
    @TypeConverter
    // Función de conversión: Convierte un valor de tipo Long a un objeto Date
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    // Función de conversión: Convierte un objeto Date a un valor de tipo Long
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
    //estas dos funciones trabajan en conjunto para
    //permitir que la clase Note se almacene y recupere de la base de datos
}