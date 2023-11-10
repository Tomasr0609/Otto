package com.example.tpfinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomexample.R

class NotesRVAdapter : ListAdapter<Note, NotesRVAdapter.NoteHolder>(DiffCallback()) {

    class NoteHolder(view: View) : RecyclerView.ViewHolder(view)
    // Clase interna que representa un ViewHolder para cada elemento en el RecyclerView


    private lateinit var listener: RecyclerClickListener
    fun setItemListener(listener: RecyclerClickListener) {
        this.listener = listener
    }



    // Método llamado cuando se necesita crear un nuevo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.notes_row, parent, false)
        val noteHolder = NoteHolder(v)
        // Inflar el diseño de la fila (notes_row) para cada elemento del RecyclerView


        val noteDelete = noteHolder.itemView.findViewById<ImageView>(R.id.note_delete)
        noteDelete.setOnClickListener {
            // Configura los clics en los elementos de la fila note_delete

            listener.onItemRemoveClick(noteHolder.adapterPosition)
            // Llama al método onItemRemoveClick del listener cuando se hace clic en el icono de eliminar

        }


        val note = noteHolder.itemView.findViewById<CardView>(R.id.note)
        note.setOnClickListener {

            listener.onItemClick(noteHolder.adapterPosition)
            // Llama al método onItemClick del listener cuando se hace clic en la nota

        }

        return noteHolder
    }


    // Método llamado para vincular datos específicos a una vista
    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentItem = getItem(position)

        val noteText = holder.itemView.findViewById<TextView>(R.id.note_text)
        noteText.text = currentItem.noteText
        // Obtener la referencia a la vista de texto en la fila (note_text) y establecer el texto de la nota

    }


    // Clase que implementa la lógica de comparación para mejorar la eficiencia en actualizaciones del RecyclerView
    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.dateAdded == newItem.dateAdded

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem
    }
}

