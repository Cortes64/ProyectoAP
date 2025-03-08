package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.altrik.proyectoap.utilities.request.LoginRequest
import com.altrik.proyectoap.utilities.response.LoginResponse
import com.altrik.proyectoap.utilities.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    // Se crea la instancia de la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        // Se le agrega funcionalidad al boton de siguiente
        val btnNext = findViewById<Button>(R.id.boton_enviar)
        btnNext.setOnClickListener {
            validarLogin()
        }

        // Se le agrega funcionalidad al boton de registro
        val btnSignIn = findViewById<Button>(R.id.boton_registro)
        btnSignIn.setOnClickListener {
            irRegistro()
        }

    }

    private fun irRegistro() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irMenu() {
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun validarLogin() {
        val campoCorreo = findViewById<EditText>(R.id.InputCorreo)
        val campoContrasena = findViewById<EditText>(R.id.InputContraseña)

        val correo = campoCorreo.text.toString()
        val contrasena = campoContrasena.text.toString()

        if (validarCampos(correo = correo, contrasena = contrasena)) {
            Toast.makeText(this, "Por favor complete todos los campos del login", Toast.LENGTH_SHORT).show()
            return
        }

        val apiService = RetrofitClient.apiService
        val loginRequest = LoginRequest(correo, contrasena)

        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // Si el login es exitoso
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.success) {
                        // Redirigir al menu principal
                        irMenu()
                    } else {
                        // Mostrar el mensaje de error si no es exitoso
                        Toast.makeText(this@LoginActivity, loginResponse?.message ?: "Error desconocido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Error en el login", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // En caso de que falle la llamada a la API
                Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validarCampos(correo: String, contrasena: String): Boolean {
        return correo.isEmpty() || contrasena.isEmpty()
    }
}