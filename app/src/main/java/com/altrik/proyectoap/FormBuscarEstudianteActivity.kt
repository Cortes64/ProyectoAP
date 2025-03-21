package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FormBuscarEstudianteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_buscar_estudiante_layout)

        val botonBuscar = findViewById<Button>(R.id.boton_buscar)
        botonBuscar.setOnClickListener {
            buscarEstudiante()
        }
    }

    private fun buscarEstudiante() {
        val inputNombreEstudiante = findViewById<EditText>(R.id.inputNombreEstudiante)
        val nombreEstudiante = inputNombreEstudiante.text.toString()

        if (campoVacio(nombreEstudiante)) {
            Toast.makeText(this, "Por favor, ingrese un nombre", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPref = getSharedPreferences("SearchUser", MODE_PRIVATE).edit()
        sharedPref.putString("nombreBuscado", nombreEstudiante)
        sharedPref.apply()

        val intent = Intent(this, UserListActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun campoVacio(nombre: String): Boolean {
        return nombre.isEmpty()
    }
}