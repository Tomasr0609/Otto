package com.example.tpfinal

interface RecyclerClickListener {
    fun onItemRemoveClick(position: Int)
    //Esta función se llama cuando se realiza un clic en un elemento de la lista con la intención de eliminar ese elemento
    fun onItemClick(position: Int)
    //Esta función se llama cuando se realiza un clic en un elemento de la lista con
    // algún propósito general, por ejemplo, para editar el elemento.
}