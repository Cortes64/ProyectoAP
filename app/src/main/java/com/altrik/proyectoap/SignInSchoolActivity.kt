package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class SignInSchoolActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_school_layout)

        val spinner: Spinner = findViewById(R.id.spinnerOptions)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.careers_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val btnRegistrar = findViewById<Button>(R.id.boton_enviar)
        btnRegistrar.setOnClickListener {
            validarRegistro()
        }

        val btnLogin = findViewById<Button>(R.id.boton_login)
        btnLogin.setOnClickListener {
            irLogin()
        }
    }

    // Acciones que provocan los botones

    private fun validarRegistro() {
        val campoCorreo = findViewById<EditText>(R.id.InputCorreo)
        val campoNombre = findViewById<EditText>(R.id.inputNombre)
        val campoFacultad = findViewById<EditText>(R.id.inputFacultad)
        val campoTelefono = findViewById<EditText>(R.id.inputTelefono)
        val campoContrasena = findViewById<EditText>(R.id.InputContraseña)
        val campoConfirmarContrasena = findViewById<EditText>(R.id.InputRepetirContraseña)
        val spinner = findViewById<Spinner>(R.id.spinnerOptions)

        val correo = campoCorreo.text.toString()
        val nombre = campoNombre.text.toString()
        val facultad = campoFacultad.text.toString()
        val telefono = campoTelefono.text.toString()
        val contrasena = campoContrasena.text.toString()
        val confirmarContrasena = campoConfirmarContrasena.text.toString()
        val carrera = spinner.selectedItem.toString()


    }

    private fun irLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}