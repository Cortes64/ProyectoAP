package com.altrik.proyectoap

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.lifecycleScope
import com.altrik.proyectoap.utilities.MailSender
import com.altrik.proyectoap.utilities.RetrofitClient
import com.altrik.proyectoap.utilities.Usuario
import com.altrik.proyectoap.utilities.response.UserListResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignInProfessorActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_professor_layout)

        val spinner = findViewById<Spinner>(R.id.EscuelaOptions)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.array_lugares_trabajo,
            R.layout.spinner
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val btnLogin = findViewById<Button>(R.id.boton_inicio_sesion)
        btnLogin.setOnClickListener {
            irLogin()
        }

        val btnCrearCuenta = findViewById<Button>(R.id.boton_crear_cuenta)
        btnCrearCuenta.setOnClickListener {
            crearCuenta()
        }
    }

    // Al presionar los botones

    private fun crearCuenta() {
        val correo = findViewById<EditText>(R.id.InputCorreo).text.toString()
        val nombre = findViewById<EditText>(R.id.inputNombre).text.toString()
        val apellidos = findViewById<EditText>(R.id.inputApellidos).text.toString()
        val contrasena = findViewById<EditText>(R.id.InputContraseña).text.toString()
        val repetirContrasena = findViewById<EditText>(R.id.InputRepetirContraseña).text.toString()
        val telefono = findViewById<EditText>(R.id.InputTelefono).text.toString()
        val departamentoAcademico = findViewById<EditText>(R.id.InputDepartamentoAcademico).text.toString()

        val spinner = findViewById<Spinner>(R.id.EscuelaOptions)
        val escuela = spinner.selectedItem.toString()

        if (camposVacios(correo, nombre, apellidos, escuela, telefono, departamentoAcademico, contrasena, repetirContrasena)) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (!validarCorreo(correo)) {
            Toast.makeText(this, "El correo debe terminar en @itcr.ac.cr o @tec.ac.cr", Toast.LENGTH_SHORT).show()
            return
        }

        if (!validarContransena(contrasena, repetirContrasena)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        val codigoVerificacion = generarCodigoVerificacion()

        val asunto = "Código de Verificación"
        val cuerpo = "Tu código de verificación es: $codigoVerificacion"

        MailSender.sendEmail(this, correo, asunto, cuerpo)

        mostrarPantallaVerificacion(
            codigoVerificacion = codigoVerificacion,
            correo = correo,
            nombre = nombre,
            apellidos = apellidos,
            escuela = escuela,
            contrasena = contrasena,
            departamentoAcademico = departamentoAcademico,
            telefono = telefono
        )
    }


    // Validaciones al crear la cuenta

    private fun camposVacios(
        correo: String,
        nombre: String,
        apellidos: String,
        escuela: String,
        telefono: String,
        departamentoAcademico: String,
        contrasena: String,
        repetirContrasena: String
    ): Boolean {
        val camposVacios = correo.isEmpty() ||
                nombre.isEmpty() ||
                apellidos.isEmpty() ||
                escuela.isEmpty() ||
                contrasena.isEmpty() ||
                repetirContrasena.isEmpty() ||
                telefono.isEmpty() ||
                departamentoAcademico.isEmpty()
        return camposVacios
    }

    private fun validarCorreo(correo: String): Boolean {
        val domain = correo.endsWith("@itcr.ac.cr") || correo.endsWith("@tec.ac.cr")
        return domain
    }

    private fun validarContransena(contrasena: String, repetirContrasena: String): Boolean {
        val passwordMatch = contrasena == repetirContrasena
        return passwordMatch
    }

    private fun generarCodigoVerificacion(): String {
        val caracteres = "0123456789"
        val longitud = 6
        val codigo = StringBuilder()

        for (i in 0 until longitud) {
            val indice = (0 until caracteres.length).random()
            codigo.append(caracteres[indice])
        }

        return codigo.toString()
    }

    private fun mostrarPantallaVerificacion(
        codigoVerificacion: String,
        correo: String,
        nombre: String,
        apellidos: String,
        escuela: String,
        contrasena: String,
        departamentoAcademico: String,
        telefono: String
    ) {
        val sharedPreferences = getSharedPreferences("SignInPrefs", MODE_PRIVATE).edit()
        sharedPreferences.putString("codigoVerificacion", codigoVerificacion)
        sharedPreferences.putString("correo", correo)
        sharedPreferences.putString("nombre", nombre)
        sharedPreferences.putString("apellidos", apellidos)
        sharedPreferences.putString("escuela", escuela)
        sharedPreferences.putString("contrasena", contrasena)
        sharedPreferences.putString("tipoUsuario", "PROFESOR")
        sharedPreferences.putString("departamentoAcademico", departamentoAcademico)
        sharedPreferences.putString("telefono", telefono)
        sharedPreferences.apply()

        val intent = Intent(this, VerificacionActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}