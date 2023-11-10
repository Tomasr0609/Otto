package com.example.tpfinal

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(

    entities = [Note::class],
    //Entidad "Note" asociada a la base de datos
    version = 1,
    exportSchema = true
)
@TypeConverters(NoteConverters::class)

abstract class NoteDatabase : RoomDatabase() {
    // Clase que extiende RoomDatabase, representando la base de datos


    abstract fun noteDao(): NoteDao
    // Método que devuelve una instancia del DAO asociado con esta base de datos
    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null
        // Objeto que proporciona una garantía de que el valor de INSTANCE siempre es el último escrito


        fun getDatabase(context: Context): NoteDatabase {
            // Método que devuelve una instancia única de la base de datos
            //////////////////

            // Si INSTANCE no es nula, retorna.
            // Si es nula, crea la base de datos.
            if (INSTANCE == null) {
                synchronized(this) {
                    // Dentro de la sección sincronizada, verifica nuevamente si INSTANCE es nula
                    // y crea la base de datos si es necesario
                    INSTANCE = buildDatabase(context)
                }
            }

            return INSTANCE!!
            // Retorna la base de datos
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            // Migración de la versión 1 a la versión 2 de la base de datos
            override fun migrate(database: SupportSQLiteDatabase) {
                // La siguiente consulta SQL agregará una nueva columna llamada lastUpdate a la base de datos de notas.
                database.execSQL("ALTER TABLE notes ADD COLUMN lastUpdate INTEGER NOT NULL DEFAULT 0")
            }
        }

        private fun buildDatabase(context: Context): NoteDatabase {
            // Método para construir la base de datos
            return Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "notes_database"
            )

                .build()
        }
    }
}