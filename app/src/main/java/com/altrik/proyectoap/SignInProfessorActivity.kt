package com.altrik.proyectoap

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.net.toUri
import android.util.Log
import kotlin.concurrent.thread

class SignInProfessorActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_professor_layout)

        val btnRegistrar = findViewById<Button>(R.id.boton_enviar)
        btnRegistrar.setOnClickListener {
            validarRegistro()
        }

        val btnLogin = findViewById<Button>(R.id.boton_login)
        btnLogin.setOnClickListener {
            irLogin()
        }
    }

    //Validaciones para el SignIn

    /**
     * Valida que los campos no estén vacíos
     *
     * @param correo Correo del profesor
     * @param nombre Nombre del profesor
     * @param telefono Teléfono del profesor
     * @param departamento Departamento del profesor
     * @param contrasena Contraseña del profesor
     * @param confirmarContrasena Confirmación de la contraseña del profesor
     *
     * @return True si los campos están vacíos, false en caso contrario
     */
    private fun camposVacios(
        correo: String,
        nombre: String,
        telefono: String,
        departamento: String,
        contrasena: String,
        confirmarContrasena: String
    ): Boolean {
        return correo.isEmpty() ||
                nombre.isEmpty() ||
                telefono.isEmpty() ||
                departamento.isEmpty() ||
                contrasena.isEmpty() ||
                confirmarContrasena.isEmpty()
    }

    /**
     * Valida que el correo sea válido
     *
     * @param correo Correo del profesor
     *
     * @return True si el correo es válido, false en caso contrario
     */
    private fun correoValido(correo: String): Boolean {
        return correo.endsWith("@itcr.ac.cr") || correo.endsWith("tec.ac.cr")
    }

    /**
     * Valida que las contraseñas sean iguales
     *
     * @param contrasena Contraseña del profesor
     * @param confirmarContrasena Confirmación de la contraseña del profesor
     *
     * @return True si las contraseñas son iguales, false en caso contrario
     */
    private fun confirmarContrasenas(
        contrasena: String,
        confirmarContrasena: String
    ): Boolean {
        return contrasena == confirmarContrasena
    }

    // Acciones que provocan los botones

    private fun validarRegistro() {
        val campoCorreo = findViewById<EditText>(R.id.InputCorreo)
        val campoNombre = findViewById<EditText>(R.id.inputNombre)
        val campoTelefono = findViewById<EditText>(R.id.inputTelefono)
        val campoDepartamento = findViewById<EditText>(R.id.inputDepartamento)
        val campoContrasena = findViewById<EditText>(R.id.InputContraseña)
        val campoConfirmarContrasena = findViewById<EditText>(R.id.InputRepetirContraseña)

        val correo = campoCorreo.text.toString()
        val nombre = campoNombre.text.toString()
        val telefono = campoTelefono.text.toString()
        val departamento = campoDepartamento.text.toString()
        val contrasena = campoContrasena.text.toString()
        val confirmarContrasena = campoConfirmarContrasena.text.toString()

        if (camposVacios(
                correo,
                nombre,
                telefono,
                departamento,
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