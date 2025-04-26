package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import com.altrik.proyectoap.utilities.MailSender
import com.altrik.proyectoap.utilities.PromedioExtractor
import com.altrik.proyectoap.utilities.TecDigitalHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInStudentActivity : AppCompatActivity()  {
    private val REQUEST_CODE_FILE_PICKER = 1001
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_student_layout)

        val spinnerCarrera = findViewById<Spinner>(R.id.carreraOptions)
        val spinnerNivelAcademico = findViewById<Spinner>(R.id.nivelAcademicoOptions)

        val adapterCarrera = ArrayAdapter.createFromResource(
            this,
            R.array.careers_array,
            R.layout.spinner
        )

        adapterCarrera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCarrera.adapter = adapterCarrera

        val adapterNivelAcademico = ArrayAdapter.createFromResource(
            this,
            R.array.nivel_academico,
            R.layout.spinner
        )

        adapterNivelAcademico.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNivelAcademico.adapter = adapterNivelAcademico

        val btnLogin = findViewById<Button>(R.id.boton_inicio_sesion)
        btnLogin.setOnClickListener {
            irLogin()
        }

        val btnCrearCuenta = findViewById<Button>(R.id.boton_crear_cuenta)
        btnCrearCuenta.setOnClickListener {
            crearCuenta()
        }

        val btnSubirArchivo = findViewById<ImageButton>(R.id.boton_adjuntar_archivo)
        btnSubirArchivo.setOnClickListener {
            seleccionarArchivo()
        }
    }

    // Al presionar los botones

    private fun crearCuenta() {
        val correo = findViewById<EditText>(R.id.InputCorreo).text.toString()
        val nombre = findViewById<EditText>(R.id.inputNombre).text.toString()
        val apellidos = findViewById<EditText>(R.id.inputApellidos).text.toString()
        val carnet = findViewById<EditText>(R.id.InputCarnet).text.toString()
        val contrasena = findViewById<EditText>(R.id.InputContraseña).text.toString()
        val contacto = findViewById<EditText>(R.id.InputContacto).text.toString()

        val spinnerCarrera = findViewById<Spinner>(R.id.carreraOptions)
        val spinnerNivelAcademico = findViewById<Spinner>(R.id.nivelAcademicoOptions)

        val carrera = spinnerCarrera.selectedItem.toString()
        val nivelAcademico = spinnerNivelAcademico.selectedItem.toString()

        if (camposVacios(correo, nombre, apellidos, carnet, contrasena, contacto)) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (!validarCorreo(correo)) {
            Toast.makeText(this, "El correo debe terminar en @estudiantec.cr", Toast.LENGTH_SHORT).show()
            return
        }

        val codigoVerificacion = generarCodigoVerificacion()

        val asunto = "Código de Verificación"
        val cuerpo = "Tu código de verificación es: $codigoVerificacion"

        MailSender.sendEmail(this, correo, asunto, cuerpo)

        CoroutineScope(Dispatchers.IO).launch {
            val promedioApi = TecDigitalHelper().obtenerPromedioTec(correo, contrasena)

            withContext(Dispatchers.Main) {

                when (promedioApi) {
                    "ERR_401" -> {
                        Toast.makeText(this@SignInStudentActivity, "Contraseña o correo incorrectos", Toast.LENGTH_SHORT).show()
                    }
                    "ERR_NO_RESPONSE" -> {
                        Toast.makeText(this@SignInStudentActivity, "Error al obtener el promedio", Toast.LENGTH_SHORT).show()
                    }
                    "ERR_PARSE" -> {
                        Toast.makeText(this@SignInStudentActivity, "Error al obtener el promedio", Toast.LENGTH_SHORT).show()
                    }
                    "ERR_DESCONOCIDO" -> {
                        Toast.makeText(this@SignInStudentActivity, "Ocurrió un error desconocido", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        mostrarPantallaVerificacion(
                            codigoVerificacion = codigoVerificacion,
                            correo = correo,
                            nombre = nombre,
                            apellidos = apellidos,
                            carnet = carnet,
                            contrasena = contrasena,
                            contacto = contacto,
                            carrera = carrera,
                            nivelAcademico = nivelAcademico,
                            promedioPonderado = promedioApi.toString()
                        )
                    }
                }
            }
        }
    }

    private fun irLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun seleccionarArchivo() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*" // Puedes especificar "application/pdf", "image/*", etc.
        startActivityForResult(Intent.createChooser(intent, "Seleccione un archivo"), REQUEST_CODE_FILE_PICKER)
    }

    // Validaciones al crear la cuenta

    private fun camposVacios(
        correo: String,
        nombre: String,
        apellidos: String,
        carnet: String,
        contrasena: String,
        contacto: String,
    ): Boolean {
        val camposVacios = correo.isEmpty() ||
                nombre.isEmpty() ||
                apellidos.isEmpty() ||
                carnet.isEmpty() ||
                contrasena.isEmpty() ||
                contacto.isEmpty()
        return camposVacios
    }

    private fun validarCorreo(correo: String): Boolean {
        val domain = correo.endsWith("@estudiantec.cr")
        return domain
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
        carnet: String,
        contrasena: String,
        contacto: String,
        carrera: String,
        nivelAcademico: String,
        promedioPonderado: String
    ) {
        val sharedPreferences = getSharedPreferences("SignInPrefs", MODE_PRIVATE).edit()
        sharedPreferences.putString("codigoVerificacion", codigoVerificacion)
        sharedPreferences.putString("correo", correo)
        sharedPreferences.putString("nombre", nombre)
        sharedPreferences.putString("apellidos", apellidos)
        sharedPreferences.putString("carnet", carnet)
        sharedPreferences.putString("contrasena", contrasena)
        sharedPreferences.putString("tipoUsuario", "ESTUDIANTE")
        sharedPreferences.putString("contacto", contacto)
        sharedPreferences.putString("carrera", carrera)
        sharedPreferences.putString("nivelAcademico", nivelAcademico)
        sharedPreferences.putString("promedioPonderado", promedioPonderado)
        sharedPreferences.apply()

        val intent = Intent(this, VerificacionActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === REQUEST_CODE_FILE_PICKER && resultCode == RESULT_OK) {
            val fileUri = data?.data
            if (fileUri != null) {
                Toast.makeText(this, "Archivo seleccionado: $fileUri", Toast.LENGTH_SHORT).show()
            }
        }
    }
}