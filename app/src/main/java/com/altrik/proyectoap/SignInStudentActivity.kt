package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.altrik.proyectoap.utilities.RetrofitClient
import com.altrik.proyectoap.utilities.request.SignInRequest
import com.altrik.proyectoap.utilities.response.SignInResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInStudentActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_student_layout)

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
        val escuela = findViewById<EditText>(R.id.InputCarnet).text.toString()
        val contrasena = findViewById<EditText>(R.id.InputContraseña).text.toString()
        val repetirContrasena = findViewById<EditText>(R.id.InputRepetirContraseña).text.toString()

        if (camposVacios(correo, nombre, apellidos, escuela, contrasena, repetirContrasena)) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (!validarCorreo(correo)) {
            Toast.makeText(this, "El correo debe terminar en @estudiantec.cr", Toast.LENGTH_SHORT).show()
            return
        }

        if (!validarContransena(contrasena, repetirContrasena)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        val apiService = RetrofitClient.apiService
        val signInRequest = SignInRequest(
            email = correo,
            name = nombre,
            apellidos = apellidos,
            escuela = escuela,
            password = contrasena,
            tipoUsuario = "ESTUDIANTE",
            zonaTrabajo = null
        )

        apiService.signIn(signInRequest).enqueue(object : Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                if (response.isSuccessful) {
                    val signInResponse = response.body()
                    if (signInResponse != null && signInResponse.success) {
                        Toast.makeText(this@SignInStudentActivity, signInResponse.message, Toast.LENGTH_SHORT).show()
                        irLogin()
                    } else {
                        Toast.makeText(this@SignInStudentActivity, signInResponse?.message ?: "Error desconocido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                    Toast.makeText(this@SignInStudentActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                Toast.makeText(this@SignInStudentActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun irLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Validaciones al crear la cuenta

    private fun camposVacios(
        correo: String,
        nombre: String,
        apellidos: String,
        escuela: String,
        contrasena: String,
        repetirContrasena: String
    ): Boolean {
        val camposVacios = correo.isEmpty() ||
                nombre.isEmpty() ||
                apellidos.isEmpty() ||
                escuela.isEmpty() ||
                contrasena.isEmpty() ||
                repetirContrasena.isEmpty()
        return camposVacios
    }

    private fun validarCorreo(correo: String): Boolean {
        val domain = correo.endsWith("@estudiantec.cr")
        return domain
    }

    private fun validarContransena(contrasena: String, repetirContrasena: String): Boolean {
        val passwordMatch = contrasena == repetirContrasena
        return passwordMatch
    }
}