package com.example.tpfinal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomexample.R
import kotlinx.coroutines.launch
import java.util.*

class NotesActivity : AppCompatActivity() {

    private lateinit var adapter: NotesRVAdapter
    // Adaptador para el RecyclerView que muestra las notas


    private val noteDatabase by lazy { NoteDatabase.getDatabase(this).noteDao() }
    // Instancia del DAO de la base de datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        // Configura el RecyclerView y observa las notas
        setRecyclerView()
        observeNotes()
    }

    private fun setRecyclerView() {
        val notesRecyclerview = findViewById<RecyclerView>(R.id.notes_recyclerview)


        notesRecyclerview.layoutManager = LinearLayoutManager(this)
        notesRecyclerview.setHasFixedSize(true)
        adapter = NotesRVAdapter()
        // Configura el LayoutManager y el adaptador


        adapter.setItemListener(object : RecyclerClickListener {
            // Configura los listeners para el tap/click en elementos del RecyclerView



            // Apretar la X para borrar la nota creada
            override fun onItemRemoveClick(position: Int) {
                val notesList = adapter.currentList.toMutableList()
                val noteText = notesList[position].noteText
                val noteDateAdded = notesList[position].dateAdded
                val removeNote = Note(noteDateAdded, noteText)
                notesList.removeAt(position)
                adapter.submitList(notesList)
                lifecycleScope.launch {
                    noteDatabase.deleteNote(removeNote)
                }
            }

            // Tocar la nota para editarla
            override fun onItemClick(position: Int) {
                val intent = Intent(this@NotesActivity, AddNoteActivity::class.java)
                val notesList = adapter.currentList.toMutableList()
                intent.putExtra("note_date_added", notesList[position].dateAdded)
                intent.putExtra("note_text", notesList[position].noteText)
                editNoteResultLauncher.launch(intent)
            }
        })
        notesRecyclerview.adapter = adapter
        // Configura el adaptador para el RecyclerView
    }

    private fun observeNotes() {
        // Observa la base de datos para cambios en las notas
        lifecycleScope.launch {
            noteDatabase.getNotes().collect { notesList ->
                if (notesList.isNotEmpty()) {
                    adapter.submitList(notesList)
                }
            }
        }
    }

    //Qué pasa cuando se agrega una nota?
    private val newNoteResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val noteDateAdded = Date()
                val noteText = result.data?.getStringExtra("note_text")
                // Obtiene la nueva nota desde la actividad AddNoteActivity.


                val newNote = Note(noteDateAdded, noteText ?: "")
                lifecycleScope.launch {
                    noteDatabase.addNote(newNote)
                    // Agrega la nueva nota en la parte superior de la lista.
                }
            }
        }

    //Qué pasa cuando se edita una nota?
    val editNoteResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val noteDateAdded = result.data?.getSerializableExtra("note_date_added") as Date
                val noteText = result.data?.getStringExtra("note_text")
                // Obtiene la nota editada desde la actividad AddNoteActivity.


                val editedNote = Note(noteDateAdded, noteText ?: "")
                lifecycleScope.launch {
                    noteDatabase.updateNote(editedNote)
                    // Actualiza la nota en la lista.
                }
            }
        }


    // El menu "+" para agregar
    //Esta función se llama cuando un elemento del menú de la barra de acciones se selecciona.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_note_menu_item) {
        //Verifica si el elemento del menú seleccionado es el que tiene el ID add_note_menu_item.

            val intent = Intent(this, AddNoteActivity::class.java)
            newNoteResultLauncher.launch(intent)
            return true
            // Abre "AddNoteActivity" para agregar una nueva nota
        }
        return super.onOptionsItemSelected(item)
    }



    //Este método se llama durante la creación de la actividad para inflar el menú en la barra de acciones.
    //En este caso se está inflando un menú desde un layout o XML para que pueda ser utilizado en la barra de acciones de la actividad.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notes, menu)
        return true
        // Crea el menú en la barra de acciones
    }
}