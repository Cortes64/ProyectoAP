package com.altrik.proyectoap

import android.os.Bundle
import android.app.DatePickerDialog
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
import com.altrik.proyectoap.utilities.ApiService
import com.altrik.proyectoap.utilities.Oferta
import com.altrik.proyectoap.utilities.RetrofitClient
import com.altrik.proyectoap.utilities.response.OfertaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch



class CrearOfertaActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var inputTitulo: EditText
    private lateinit var spinnerTipoOferta: Spinner
    private lateinit var inputObjetivos: EditText
    private lateinit var inputVacantes: EditText
    private lateinit var inputDuracion: EditText
    private lateinit var inputFechaInicio: EditText
    private lateinit var inputFechaFin: EditText
    private lateinit var inputRequisitos: EditText
    private lateinit var spinnerDepartamento: Spinner
    private lateinit var spinnerProfesor: Spinner
    private lateinit var inputDescripcion: EditText
    private lateinit var botonCrear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_crear_oferta)

        apiService = RetrofitClient.apiService

        Inicializarinputs()
        configspinners()
        datepicker()
        ButtonCrearOferta()

    }

    private fun Inicializarinputs(){
        inputTitulo = findViewById(R.id.inputTituloOferta)
        spinnerTipoOferta = findViewById(R.id.TipoOfertaOptions)
        inputObjetivos = findViewById(R.id.InputObjetivos)
        inputVacantes = findViewById(R.id.InputVacantes)
        inputDuracion = findViewById(R.id.InputDuracion)
        inputFechaInicio = findViewById(R.id.InputFechaInicio)
        inputFechaFin = findViewById(R.id.InputFechaFin)
        inputRequisitos = findViewById(R.id.InputRequisitos)
        spinnerDepartamento = findViewById(R.id.DepartamentoOptions)
        spinnerProfesor = findViewById(R.id.ProfesorOptions)
        inputDescripcion = findViewById(R.id.inputDescripcionOferta)
        botonCrear = findViewById(R.id.boton_crear)
    }

    private fun configspinners(){
        ArrayAdapter.createFromResource(
            this, R.array.Tipos_de_trabajo, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTipoOferta.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.array_lugares_trabajo, // Create this in res/values/arrays.xml
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDepartamento.adapter = adapter
        }
    }

    private fun datepicker(){
        configdatepicker(inputFechaInicio)
        configdatepicker(inputFechaFin)
    }

    private fun configdatepicker(editText: EditText){
        editText.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, day)
                    editText.setText(formattedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }
    private fun ButtonCrearOferta() {
        botonCrear.setOnClickListener {
            if (validateInputs()) {
                createOferta()
            }
        }
    }

    private fun validateInputs(): Boolean {
        return when {
            inputTitulo.text.isEmpty() -> {
                showError("El título es obligatorio")
                false
            }
            inputFechaInicio.text.isEmpty() -> {
                showError("Seleccione fecha de inicio")
                false
            }
            inputFechaFin.text.isEmpty() -> {
                showError("Seleccione fecha de fin")
                false
            }
            else -> true
        }
    }

    private fun createOferta() {
        val oferta = Oferta(
            titulo = inputTitulo.text.toString(),
            tipoTrabajo = spinnerTipoOferta.selectedItem.toString(),
            departamento = spinnerDepartamento.selectedItem.toString(),
            descripcion = inputDescripcion.text.toString(),
            profesor = "Profesor Perez", //De momento no se que hacer aca, pero idealmente hay que hacer que pueda jalar values de la DB
            fechaInicio = inputFechaInicio.text.toString(),
            fechaFin = inputFechaFin.text.toString(),
            objetivos = inputObjetivos.text.toString(),
            cantidadVacantes = inputVacantes.text.toString(),
            duracion = inputDuracion.text.toString(),
            requisitos = inputRequisitos.text.toString(),
            estadoOferta = "ABIERTA"
        )

        apiService.createOferta(oferta).enqueue(object : Callback<OfertaResponse> {
            override fun onResponse(call: Call<OfertaResponse>, response: Response<OfertaResponse>) {
                if (response.isSuccessful) {
                    handleSuccess(response.body())
                } else {
                    handleError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<OfertaResponse>, t: Throwable) {
                handleError("Error de conexión: ${t.message}")
            }
        })
    }

    private fun handleSuccess(response: OfertaResponse?) {
        if (response?.success == true) {
            Toast.makeText(this, "Oferta creada exitosamente", Toast.LENGTH_SHORT).show()
            finish() // Close activity after success
        } else {
            handleError(response?.message ?: "Error desconocido")
        }
    }

    private fun handleError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


