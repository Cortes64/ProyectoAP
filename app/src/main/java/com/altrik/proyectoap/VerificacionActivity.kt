package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.altrik.proyectoap.utilities.ApiService
import com.altrik.proyectoap.utilities.RetrofitClient
import com.altrik.proyectoap.utilities.request.SignInRequest
import com.altrik.proyectoap.utilities.response.SignInResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VerificacionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verificacion_layout)

        val btnVerificar = findViewById<Button>(R.id.boton_verificar)
        btnVerificar.setOnClickListener {
            verificarCodigo()
        }

        val btnInicioSesion = findViewById<Button>(R.id.boton_login)
        btnInicioSesion.setOnClickListener {
            irLogin()
        }
    }

    private fun verificarCodigo() {
        val sharedPref = getSharedPreferences("SignInPrefs", MODE_PRIVATE)

        val codigoIngresado = findViewById<EditText>(R.id.inputCodigo).text.toString()
        val codigoVerificacion = sharedPref.getString("codigoVerificacion", "") ?: ""

        if (codigoIngresado.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese el código de verificación", Toast.LENGTH_SHORT).show()
            return
        }

        if (codigoIngresado != codigoVerificacion) {
            Toast.makeText(this, "Código de verificación incorrecto", Toast.LENGTH_SHORT).show()
            return
        }

        val tipoUsuario = sharedPref.getString("tipoUsuario", "") ?: ""

        when (tipoUsuario) {
            "ESTUDIANTE" -> crearEstudiante()
            "PROFESOR" -> crearProfesor()
            "ESCUELA" -> crearEscuelaOAdmin()
            "ADMIN" -> crearEscuelaOAdmin()
            else -> Toast.makeText(this, "Tipo de usuario no reconocido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun crearEstudiante() {
        val apiService = RetrofitClient.apiService

        val sharedPref = getSharedPreferences("SignInPrefs", MODE_PRIVATE)
        val correo = sharedPref.getString("correo", "") ?: ""
        val nombre = sharedPref.getString("nombre", "") ?: ""
        val apellidos = sharedPref.getString("apellidos", "") ?: ""
        val carnet = sharedPref.getString("carnet", "") ?: ""
        val contrasena = sharedPref.getString("contrasena", "") ?: ""
        val tipoUsuario = sharedPref.getString("tipoUsuario", "") ?: ""
        val contacto = sharedPref.getString("contacto", "") ?: ""
        val carrera = sharedPref.getString("carrera", "") ?: ""
        val nivelAcademico = sharedPref.getString("nivelAcademico", "") ?: ""
        val promedioPonderado = sharedPref.getString("promedioPonderado", "") ?: ""

        val signInRequest = SignInRequest(
            email = correo,
            name = nombre,
            apellidos = apellidos,
            carnet = carnet,
            password = contrasena,
            tipoUsuario = tipoUsuario,
            escuela = null,
            zonaTrabajo = null,
            departamentoTrabajo = null,
            telefono = null,
            nivelAcademico = nivelAcademico,
            contacto = contacto,
            carrera = carrera,
            promedioPonderado = promedioPonderado
        )

        if (
            !vacio(signInRequest.email) &&
            !vacio(signInRequest.name) &&
            !vacio(signInRequest.apellidos) &&
            !vacio(signInRequest.carnet) &&
            !vacio(signInRequest.password)
        ) {
            llamarApi(apiService, signInRequest)
        }
    }

    private fun crearProfesor() {
        val apiService = RetrofitClient.apiService

        val sharedPref = getSharedPreferences("SignInPrefs", MODE_PRIVATE)
        val correo = sharedPref.getString("correo", "") ?: ""
        val nombre = sharedPref.getString("nombre", "") ?: ""
        val apellidos = sharedPref.getString("apellidos", "") ?: ""
        val escuela = sharedPref.getString("escuela", "") ?: ""
        val contrasena = sharedPref.getString("contrasena", "") ?: ""
        val tipoUsuario = sharedPref.getString("tipoUsuario", "") ?: ""
        val departamentoAcademico = sharedPref.getString("departamentoAcademico", "") ?: ""
        val telefono = sharedPref.getString("telefono", "") ?: ""

        val signInRequest = SignInRequest(
            email = correo,
            name = nombre,
            apellidos = apellidos,
            carnet = null,
            password = contrasena,
            tipoUsuario = tipoUsuario,
            escuela = escuela,
            zonaTrabajo = null,
            departamentoTrabajo = departamentoAcademico,
            telefono = telefono,
            nivelAcademico = null,
            contacto = null,
            carrera = null,
            promedioPonderado = null
        )

        if (
            !vacio(signInRequest.email) &&
            !vacio(signInRequest.name) &&
            !vacio(signInRequest.apellidos) &&
            !vacio(signInRequest.password) &&
            !vacio(signInRequest.escuela)
        ) {
            llamarApi(apiService, signInRequest)
        }
    }

    private fun crearEscuelaOAdmin() {
        val apiService = RetrofitClient.apiService

        val sharedPref = getSharedPreferences("SignInPrefs", MODE_PRIVATE)
        val correo = sharedPref.getString("correo", "") ?: ""
        val nombre = sharedPref.getString("nombre", "") ?: ""
        val apellidos = sharedPref.getString("apellidos", "") ?: ""
        val zonaTrabajo = sharedPref.getString("zonaTrabajo", "") ?: ""
        val contrasena = sharedPref.getString("contrasena", "") ?: ""
        val tipoUsuario = sharedPref.getString("tipoUsuario", "") ?: ""
        val escuela = sharedPref.getString("escuela", "") ?: ""

        val signInRequest = SignInRequest(
            email = correo,
            name = nombre,
            apellidos = apellidos,
            carnet = null,
            password = contrasena,
            tipoUsuario = tipoUsuario,
            escuela = escuela,
            zonaTrabajo = zonaTrabajo,
            departamentoTrabajo = null,
            telefono = null,
            nivelAcademico = null,
            contacto = null,
            carrera = null,
            promedioPonderado = null
        )

        if (
            !vacio(signInRequest.email) &&
            !vacio(signInRequest.name) &&
            !vacio(signInRequest.apellidos) &&
            !vacio(signInRequest.password)
            && !vacio(signInRequest.zonaTrabajo)
        ) {
            llamarApi(apiService, signInRequest)
        }
    }

    private fun llamarApi(
        apiService: ApiService,
        signInRequest: SignInRequest
    ) {
        apiService.signIn(signInRequest).enqueue(object : Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                if (response.isSuccessful) {
                    val signInResponse = response.body()
                    if (signInResponse != null && signInResponse.success) {
                        Toast.makeText(this@VerificacionActivity, signInResponse.message, Toast.LENGTH_SHORT).show()
                        irLogin()
                    } else {
                        Toast.makeText(this@VerificacionActivity, signInResponse?.message ?: "Error desconocido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                    Toast.makeText(this@VerificacionActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                Toast.makeText(this@VerificacionActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun vacio(campo: String?): Boolean {
        if (campo != null) {
            return campo.isEmpty()
        }
        return false
    }

    private fun irLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}