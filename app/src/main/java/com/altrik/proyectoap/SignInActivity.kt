package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.altrik.proyectoap.utilities.RetrofitClient
import com.altrik.proyectoap.utilities.request.SignInRequest
import com.altrik.proyectoap.utilities.response.SignInResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_layout)

        val btnEstudiante = findViewById<Button>(R.id.buttonEstudiante)
        btnEstudiante.setOnClickListener {
            irRegistroEstudiante()
        }

        val btnProfesor = findViewById<Button>(R.id.buttonProfesor)
        btnProfesor.setOnClickListener {
            irRegistroProfesor()
        }

        val btnEscuela = findViewById<Button>(R.id.buttonEscuela)
        btnEscuela.setOnClickListener {
            irRegistroEscuela()
        }

        val btnAdmin = findViewById<Button>(R.id.buttonAdmin)
        btnAdmin.setOnClickListener {
            irRegistroAdmin()
        }

        val btnLogin = findViewById<Button>(R.id.boton_inicio_sesion)
        btnLogin.setOnClickListener {
            irLogin()
        }
    }

    // Al presionar los botones

    private fun irRegistroEstudiante() {
        val intent = Intent(this, SignInStudentActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irRegistroProfesor() {
        val intent = Intent(this, SignInProfessorActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irRegistroEscuela() {
        val intent = Intent(this, SignInSchoolActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irRegistroAdmin() {
        val intent = Intent(this, SignInAdminActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
