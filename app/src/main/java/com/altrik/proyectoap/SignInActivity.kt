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
            validarRegistro()
        }

        val btnLogin = findViewById<Button>(R.id.boton_login)
        btnLogin.setOnClickListener {
            irLogin()
        }
    }

    private fun irLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validarRegistro() {
        val campoCorreo = findViewById<EditText>(R.id.InputCorreo)
        val campoTelefono = findViewById<EditText>(R.id.inputTelefono)
        val campoDepartamentoAcademico = findViewById<EditText>(R.id.inputDepartamento)
        val campoNivelAcademico = findViewById<EditText>(R.id.inputNivelAcademico)
        val campoContacto = findViewById<EditText>(R.id.inputContacto)
        val campoContrasena = findViewById<EditText>(R.id.InputContraseña)
        val campoConfirmarContrasena = findViewById<EditText>(R.id.InputRepetirContraseña)

        val spinner = findViewById<Spinner>(R.id.spinnerOptions)
        val options = arrayOf(
            "Ingeniería en Computación",
            "Ingeniería en Computadores",
            "Administración de Empresas",
            "Ingeniería Ambiental",
            "Ingeniería en Materiales"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        val correo = campoCorreo.text.toString()
        val contrasena = campoContrasena.text.toString()
        val confirmarContrasena = campoConfirmarContrasena.text.toString()

        spinner.visibility = View.GONE
        campoNivelAcademico.visibility = View.GONE
        campoContacto.visibility = View.GONE
        campoDepartamentoAcademico.visibility = View.GONE
        campoTelefono.visibility = View.GONE

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioEstudiante -> {
                    spinner.visibility = View.VISIBLE
                    campoNivelAcademico.visibility = View.VISIBLE
                    campoContacto.visibility = View.VISIBLE
                    campoDepartamentoAcademico.visibility = View.GONE
                    campoTelefono.visibility = View.GONE
                }
                R.id.radioProfesor -> {
                    spinner.visibility = View.GONE
                    campoNivelAcademico.visibility = View.GONE
                    campoDepartamentoAcademico.visibility = View.VISIBLE
                    campoTelefono.visibility = View.VISIBLE
                    campoContacto.visibility = View.GONE
                }
                R.id.radioEscuelaDepartamento -> {
                    spinner.visibility = View.GONE
                    campoTelefono.visibility = View.GONE
                    campoDepartamentoAcademico.visibility = View.GONE
                    campoNivelAcademico.visibility = View.GONE
                    campoContacto.visibility = View.GONE
                }
                R.id.radioAdmin -> {
                    spinner.visibility = View.GONE
                    campoTelefono.visibility = View.GONE
                    campoDepartamentoAcademico.visibility = View.GONE
                    campoNivelAcademico.visibility = View.GONE
                    campoContacto.visibility = View.GONE
                }
            }
        }


        radioGroup.check(R.id.radioEstudiante)

        if (camposVacios(correo, contrasena, confirmarContrasena)) {
            Toast.makeText(this, "Por favor complete todos los campos del registro", Toast.LENGTH_SHORT).show()
            return
        }

        if (contrasenasDiferentes(contrasena, confirmarContrasena)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedRadioButtonId = radioGroup.checkedRadioButtonId

        if (selectedRadioButtonId == -1) {
            Toast.makeText(this, "Por favor seleccione un tipo de usuario", Toast.LENGTH_SHORT).show()
            return
        }

        val tipoUsuario: String = tipoUsuarioSeleccionado(selectedRadioButtonId)

        if (tipoUsuario == "ESTUDIANTE" && !verificarCorreoEstudiante(correo)) {
            Toast.makeText(this, "El correo electrónico debe ser un correo de tipo @estudiantec.cr", Toast.LENGTH_SHORT).show()
            return
        }

        if (tipoUsuario == "PROFESOR" && !verificarCorreoProfesor(correo)) {
            Toast.makeText(this, "El correo electrónico debe ser un correo de tipo @itcr.ac.cr", Toast.LENGTH_SHORT).show()
            return
        }

        if (!verificarCorreoEstudiante(correo) || !verificarCorreoProfesor(correo)) {
            Toast.makeText(this, "El correo electrónico debe ser un correo de tipo @estudiantec.cr o @itcr.ac.cr", Toast.LENGTH_SHORT).show()
            return
        }

        val apiService = RetrofitClient.apiService
        val signInRequest = SignInRequest(correo, contrasena, tipoUsuario)

        apiService.signIn(signInRequest).enqueue(object : Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                if (response.isSuccessful) {
                    val signInResponse = response.body()
                    if (signInResponse != null && signInResponse.success) {
                        irLogin()
                    } else {
                        Toast.makeText(this@SignInActivity, signInResponse?.message ?: "Error desconocido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@SignInActivity, "Error en el registro", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                Toast.makeText(this@SignInActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun camposVacios(correo: String, contrasena: String, confirmarContrasena: String): Boolean {
        return correo.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()
    }

    private fun contrasenasDiferentes(contrasena: String, confirmarContrasena: String): Boolean {
        return contrasena != confirmarContrasena
    }

    private fun tipoUsuarioSeleccionado(selectedRadioButtonId: Int): String {
        val tipoUsuario = when (selectedRadioButtonId) {
            R.id.radioEscuelaDepartamento -> "ESCUELA"
            R.id.radioEstudiante -> "ESTUDIANTE"
            R.id.radioProfesor -> "PROFESOR"
            else -> "ADMINISTRADOR"
        }
        return tipoUsuario
    }

    private fun verificarCorreoEstudiante(correo: String): Boolean {
        return correo.endsWith("@estudiantec.cr")
    }

    private fun verificarCorreoProfesor(correo: String): Boolean {
        return correo.endsWith("@itcr.ac.cr")
    }
}
