package com.altrik.proyectoap

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.altrik.proyectoap.utilities.FooterBarView

class VerPerfilAdministrativoActivity : AppCompatActivity() {

    private lateinit var inputNombre: EditText
    private lateinit var inputCorreo: EditText
    private lateinit var inputTipoUsuario: EditText
    private lateinit var inputZonaTrabajo: EditText
    private lateinit var inputDepartamentoTrabajo: EditText
    private lateinit var inputTelefono: EditText
    private lateinit var inputEscuela: EditText

    private val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
    private val nombre = sharedPreferences.getString("nombreUsuario", "")
    private val correo = sharedPreferences.getString("correoUsuario", "")
    private val tipoUsuario = sharedPreferences.getString("tipoUsuario", "")
    private val zonaTrabajo = sharedPreferences.getString("zonaTrabajoUsuario", "")
    private val departamentoTrabajo = sharedPreferences.getString("departamentoTrabajoUsuario", "")
    private val telefono = sharedPreferences.getString("telefonoUsuario", "")
    private val escuela = sharedPreferences.getString("escuelaUsuario", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ver_perfil_administrativo_activity)

        inputNombre = findViewById(R.id.textViewNombre)
        inputCorreo = findViewById(R.id.textCorreo)
        inputTipoUsuario = findViewById(R.id.textTipoUsuario)
        inputZonaTrabajo = findViewById(R.id.textZonaTrabajo)
        inputDepartamentoTrabajo = findViewById(R.id.textDepartamentoTrabajo)
        inputTelefono = findViewById(R.id.textTelefono)
        inputEscuela = findViewById(R.id.textEscuela)

        when (tipoUsuario) {
            "ADMINISTRADOR" -> {
                inputDepartamentoTrabajo.visibility = View.GONE
                inputTelefono.visibility = View.GONE
                inputEscuela.visibility = View.GONE
            }
            "ESCUELA" -> {
                inputDepartamentoTrabajo.visibility = View.GONE
                inputEscuela.visibility = View.GONE
            }
            "PROFESOR" -> {
                inputZonaTrabajo.visibility = View.GONE
            }
        }

        inputNombre.setText(nombre)
        inputCorreo.setText("Correo: $correo")
        inputTipoUsuario.setText("Tipo de usuario: $tipoUsuario")
        inputZonaTrabajo.setText("Zona de trabajo: $zonaTrabajo")
        inputDepartamentoTrabajo.setText("Departamento de trabajo: $departamentoTrabajo")
        inputTelefono.setText("Teléfono: $telefono")
        inputEscuela.setText("Escuela: $escuela")

        val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val tipoUsuario = sharedPrefs.getString("tipoUsuario", "ESTUDIANTE")

        val footer = findViewById<FooterBarView>(R.id.footerBar)
        footer.configurarPara(tipoUsuario ?: "ESTUDIANTE")
    }
}