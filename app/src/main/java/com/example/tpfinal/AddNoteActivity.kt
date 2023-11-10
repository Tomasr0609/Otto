package com.example.tpfinal

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.roomexample.R
import java.util.*

@SuppressLint("RestrictedApi")
class AddNoteActivity : AppCompatActivity() {

    private lateinit var addNoteBackground: RelativeLayout
    private lateinit var addNoteWindowBg: LinearLayout
    //Se declaran dos atributos que representan elementos de la interfaz
    // de usuario (RelativeLayout y LinearLayout) que se usarán más adelante
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        //Se sobrescribe el método onCreate, que se llama cuando la actividad está
        // siendo creada, y después se establece el diseño de la actividad a partir de un archivo XML


        addNoteBackground = findViewById(R.id.add_note_background)
        addNoteWindowBg = findViewById(R.id.add_note_window_bg)
        // Inicializa las variables que representan elementos de la interfaz


        setActivityStyle()

        val noteDateAdded = intent.getSerializableExtra("note_date_added") as? Date
        val noteTextToEdit = intent.getStringExtra("note_text")
        // Obtiene la fecha y el texto de la nota del Intent que inició esta actividad



        val addNoteText = findViewById<TextView>(R.id.add_note_text)
        addNoteText.text = noteTextToEdit ?: ""
        // Configura el campo de texto con el texto de la nota si está presente



        val addNoteButton = findViewById<Button>(R.id.add_note_button)
        addNoteButton.setOnClickListener {
            // Retorna texto en formato nota a "NotesActivity"
            val data = Intent()
            data.putExtra("note_date_added", noteDateAdded)
            data.putExtra("note_text", addNoteText.text.toString())
            setResult(Activity.RESULT_OK, data)
            // Cierra la ventana activa
            onBackPressed()
        }
    }




    private fun setActivityStyle() {
        // Hace el background en pantalla completa sobre la barra de inicio
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            //Hace que el contenido se dibuje debajo de la barra de notificaciones

        this.window.statusBarColor = Color.TRANSPARENT
        //Establece el color de la barra de notificaciones como transparente.

        val winParams = this.window.attributes
        winParams.flags =
            winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        this.window.attributes = winParams


        // Cerrar el popup de la nota cuando tocas afuera
        addNoteBackground.setOnClickListener { onBackPressed() }
        addNoteWindowBg.setOnClickListener { }
        //Cierra la actividad si se toca fuera de la nota o en el área de fondo
    }
}