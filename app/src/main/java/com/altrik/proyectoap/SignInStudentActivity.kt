package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button

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

    private fun validarRegistro() {

    }

    private fun irLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}