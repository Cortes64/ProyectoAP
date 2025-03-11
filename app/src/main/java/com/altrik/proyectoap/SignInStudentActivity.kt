package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignInStudentActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_student_layout)

        val spinner: Spinner = findViewById(R.id.spinnerOptions)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.careers_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val btnRegistro = findViewById<Button>(R.id.boton_enviar)
        btnRegistro.setOnClickListener {
            validarRegistro()
        }

        val btnLogin = findViewById<Button>(R.id.boton_login)
        btnLogin.setOnClickListener {
            irLogin()
        }
    }

    // Validaciones para el SignIn

    private fun camposVacios(
        correo: String,
        nombre: String,
        telefono: String,
        nivelAcademico: String,
        contacto: String,
        contrasena: String,
        confirmarContrasena: String
    ): Boolean {
        return correo.isEmpty() ||
                nombre.isEmpty() ||
                telefono.isEmpty() ||
                nivelAcademico.isEmpty() ||
                contacto.isEmpty() ||
                contrasena.isEmpty() ||
                confirmarContrasena.isEmpty()
    }

    private fun correoValido(correo: String): Boolean {
        return correo.endsWith("@estudiantec.cr")
    }

    private fun confirmarContrasenas(
        contrasena: String,
        confirmarContrasena: String
    ): Boolean {
        return contrasena == confirmarContrasena
    }

    private fun validarRegistro() {
        val campoCorreo =  findViewById<EditText>(R.id.InputCorreo)
        val campoNombre = findViewById<EditText>(R.id.inputNombre)
        val campoTelefono = findViewById<EditText>(R.id.inputTelefono)
        val campoNivelAcademico = findViewById<EditText>(R.id.inputNivelAcademico)
        val campoContacto = findViewById<EditText>(R.id.inputContacto)
        val campoContrasena = findViewById<EditText>(R.id.InputContraseña)
        val campoConfirmarContrasena = findViewById<EditText>(R.id.InputRepetirContraseña)
        val spinner = findViewById<Spinner>(R.id.spinnerOptions)

        val correo = campoCorreo.text.toString()
        val nombre = campoNombre.text.toString()
        val telefono = campoTelefono.text.toString()
        val nivelAcademico = campoNivelAcademico.text.toString()
        val contacto = campoContacto.text.toString()
        val contrasena = campoContrasena.text.toString()
        val confirmarContrasena = campoConfirmarContrasena.text.toString()
        val selectedCareer = spinner.selectedItem.toString()

        if (camposVacios(
            correo,
            nombre,
            telefono,
            nivelAcademico,
            contacto,
            contrasena,
            confirmarContrasena
        )) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (!correoValido(correo)) {
            Toast.makeText(this, "El correo ingresado no es válido", Toast.LENGTH_SHORT).show()
            return
        }

        if (!confirmarContrasenas(contrasena, confirmarContrasena)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }


    }

    private fun irLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}