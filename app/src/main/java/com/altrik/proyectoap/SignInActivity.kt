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

        val btnNext = findViewById<Button>(R.id.boton_enviar)
        btnNext.setOnClickListener {
            btnNextClick()
        }

        val btnLogin = findViewById<Button>(R.id.boton_login)
        btnLogin.setOnClickListener {
            btnLoginClick()
        }
    }

    private fun btnNextClick() {
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId

        if (selectedRadioButtonId == -1) {
            Toast.makeText(this, "Por favor seleccione un tipo de usuario", Toast.LENGTH_SHORT).show()
            return
        }

        when (selectedRadioButtonId) {
            R.id.radioEstudiante -> {
                val intent = Intent(this, SignInStudentActivity::class.java)
                startActivity(intent)
                finish()
                return
            }
            R.id.radioProfesor -> {
                val intent = Intent(this, SignInProfessorActivity::class.java)
                startActivity(intent)
                finish()
                return
            }
            R.id.radioEscuelaDepartamento -> {
                val intent = Intent(this, SignInSchoolActivity::class.java)
                startActivity(intent)
                finish()
                return
            }
        }
        Toast.makeText(this, "Error desconocido", Toast.LENGTH_SHORT).show()
    }

    private fun btnLoginClick() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
