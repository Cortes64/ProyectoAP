package com.altrik.proyectoap

import android.content.Intent
import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.altrik.proyectoap.utilities.MailSender
import com.altrik.proyectoap.utilities.request.LoginRequest
import com.altrik.proyectoap.utilities.response.LoginResponse
import com.altrik.proyectoap.utilities.RetrofitClient
import com.altrik.proyectoap.utilities.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        val btnNext = findViewById<Button>(R.id.boton_enviar)
        btnNext.setOnClickListener {
            validarLogin()
        }

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

    private fun validarLogin() {
        val campoCorreo = findViewById<EditText>(R.id.InputCorreo)
        val campoContrasena = findViewById<EditText>(R.id.InputContraseña)

        val correo = campoCorreo.text.toString()
        val contrasena = campoContrasena.text.toString()

        if ( correo.isEmpty() || contrasena.isEmpty() ) {
            Toast.makeText(
                this,
                "Por favor, complete todos los campos del login",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val apiService = RetrofitClient.apiService
        val loginRequest = LoginRequest(correo, contrasena)

        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse?.success == true) {

                        getSharedPreferences("UserPrefs", MODE_PRIVATE)
                            .edit()
                            .apply()

                        irMenu(loginResponse.usuario)
                        return
                    } else {
                        Toast.makeText(this@LoginActivity, loginResponse?.message ?: "Error desconocido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // En este else deberían manejarse los Toasts de contraseña incorrecta y cuenta que no existe
                    Toast.makeText(this@LoginActivity, "Error en el login: ${response.code()}", Toast.LENGTH_SHORT).show()
                    response.errorBody()?.let {
                        println("Error en el login: ${it.string()}") // Imprime el error en la consola
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun irMenu(usuario: Usuario?) {
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit()
        sharedPref.putString("tipoUsuario", usuario?.tipoUsuario)
        sharedPref.putString("correoUsuario", usuario?.email)
        sharedPref.putString("nombreUsuario", usuario?.name)
        sharedPref.putString("apellidosUsuario", usuario?.apellidos)
        sharedPref.putString("carnetUsuario", usuario?.carnet)
        sharedPref.putString("escuelaUsuario", usuario?.escuela)
        sharedPref.putString("zonaTrabajoUsuario", usuario?.zonaTrabajo)
        sharedPref.putString("contactoUsuario", usuario?.contacto)
        sharedPref.apply()

        val tipoUsuario = usuario?.tipoUsuario

        when (tipoUsuario) {
            "ESTUDIANTE" -> {
                val intent = Intent(this, MenuStudentActivity::class.java)
                startActivity(intent)
                finish()
            }
            "PROFESOR" -> {
                val intent = Intent(this, MenuProfessorActivity::class.java)
                startActivity(intent)
                finish()
            }
            "ESCUELA" -> {
                val intent = Intent(this, MenuSchoolActivity::class.java)
                startActivity(intent)
                finish()
            }
            "ADMINISTRADOR" -> {
                val intent = Intent(this, MenuAdminActivity::class.java)
                startActivity(intent)
                finish()
            }
            else -> {
                Toast.makeText(this, "Tipo de usuario desconocido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}