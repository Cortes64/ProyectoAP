package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        val btnSignIn = findViewById<Button>(R.id.boton_registrarse)
        btnSignIn.setOnClickListener {
            irRegistro()
        }

    }

    private fun irRegistro() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    public fun validarLogin() {
        val campo_correo = findViewById<EditText>(R.id.InputCorreo)
        val campo_contraseña = findViewById<EditText>(R.id.InputContraseña)

        val correo = campo_correo.text.toString()
        val contraseña = campo_contraseña.text.toString()

        if(validarCampos(correo = correo, contraseña = contraseña)) {
            Toast.makeText(this, "Por favor complete todos los campos del login", Toast.LENGTH_SHORT).show()
            return
        }
    }

    private fun validarCampos(correo: String, contraseña: String): Boolean {
        return correo.isEmpty() || contraseña.isEmpty()
    }
}