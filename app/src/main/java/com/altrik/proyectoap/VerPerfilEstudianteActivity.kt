package com.altrik.proyectoap

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.altrik.proyectoap.utilities.FooterBarView

class VerPerfilEstudianteActivity : AppCompatActivity() {
    private lateinit var inputNombre: EditText
    private lateinit var inputCarnet: EditText
    private lateinit var inputCorreo: EditText
    private lateinit var inputPromedio: EditText
    private lateinit var inputContacto: EditText
    private lateinit var inputCarrera: EditText
    private lateinit var inputNivelAcademico: EditText

    private val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
    private val nombreUsuario = sharedPreferences.getString("nombreUsuario", "")
    private val apellidosUsuario = sharedPreferences.getString("apellidosUsuario", "")
    private val carnetUsuario = sharedPreferences.getString("carnetUsuario", "")
    private val correoUsuario = sharedPreferences.getString("correoUsuario", "")
    private val promedioUsuario = sharedPreferences.getString("promedioPonderadoUsuario", "")
    private val contactoUsuario = sharedPreferences.getString("contactoUsuario", "")
    private val carreraUsuario = sharedPreferences.getString("carreraUsuario", "")
    private val nivelAcademicoUsuario = sharedPreferences.getString("nivelAcademicoUsuario", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ver_perfil_estudiante_activity)

        inputNombre = findViewById(R.id.textViewNombre)
        inputCarnet = findViewById(R.id.textCarnet)
        inputCorreo = findViewById(R.id.textCorreo)
        inputPromedio = findViewById(R.id.textPromedio)
        inputContacto = findViewById(R.id.textContacto)
        inputCarrera = findViewById(R.id.textCarrera)
        inputNivelAcademico = findViewById(R.id.textNivelAcademico)

        inputNombre.setText("$nombreUsuario $apellidosUsuario")
        inputCarnet.setText("Carnet: $carnetUsuario")
        inputCorreo.setText("Correo: $correoUsuario")
        inputPromedio.setText("Promedio: $promedioUsuario")
        inputContacto.setText("Contacto: $contactoUsuario")
        inputCarrera.setText("Carrera: $carreraUsuario")
        inputNivelAcademico.setText("Nivel Académico: $nivelAcademicoUsuario")

        val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val tipoUsuario = sharedPrefs.getString("tipoUsuario", "ESTUDIANTE")

        val footer = findViewById<FooterBarView>(R.id.footerBar)
        footer.configurarPara(tipoUsuario ?: "ESTUDIANTE")
    }
}