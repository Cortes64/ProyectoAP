package com.altrik.proyectoap

import android.os.Bundle
import android.app.DatePickerDialog
import androidx.activity.enableEdgeToEdge
import android.view.animation.AnimationUtils
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
import android.graphics.Bitmap.Config
import androidx.lifecycle.lifecycleScope
import com.altrik.proyectoap.utilities.FooterBarView
import com.altrik.proyectoap.utilities.Usuario
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
    private lateinit var sidebarContainer: LinearLayout
    private var isSidebarOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_crear_oferta)

        apiService = RetrofitClient.apiService

        Inicializarinputs()

        val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val tipoUsuario = sharedPrefs.getString("tipoUsuario", "ESTUDIANTE")

        val footer = findViewById<FooterBarView>(R.id.footerBar)
        footer.configurarPara(tipoUsuario ?: "ESTUDIANTE")

        configspinners()
        datepicker()
        ButtonCrearOferta()
        fetchProfesores()
        setupSidebar()
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

    private fun fetchProfesores(){
        lifecycleScope.launch{
            try{
                val response = apiService.getUsuariosPorTipo("PROFESOR")
                if(response.success){
                    val profesores = response.data ?: emptyList()
                    runOnUiThread {
                        ConfigProfesoresSpinner(profesores)
                    }
                } else {
                    showError("No se pudo cargar los profesores")
                }
            } catch (e: Exception){ showError("Error de conexion")}
        }
    }

    private fun ConfigProfesoresSpinner(profesores: List<Usuario>){
        val professorNames = profesores.map {
            it.name ?: "Nombre no disponible"  // Handle null case
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            professorNames
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinnerProfesor.adapter = adapter
        if (professorNames.isNotEmpty()) {
            spinnerProfesor.setSelection(0)
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
            spinnerProfesor.selectedItem == null -> {
                showError("Select a professor")
                false
            }
            else -> true
        }
    }
    private fun setupSidebar() {

        sidebarContainer = findViewById(R.id.sidebarContainer)

        val sidebarButton = findViewById<ImageButton>(R.id.imageButtonSidebar2)


        sidebarButton.setOnClickListener {
            toggleSidebar()
        }


        findViewById<RelativeLayout>(R.id.mainLayout).setOnClickListener {
            if (isSidebarOpen) {
                closeSidebar()
            }
        }
    }
    private fun toggleSidebar() {
        if (isSidebarOpen) {
            closeSidebar()
        } else {
            openSidebar()
        }
        isSidebarOpen = !isSidebarOpen
    }

    private fun openSidebar() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_in)
        sidebarContainer.startAnimation(animation)
        val params = sidebarContainer.layoutParams as RelativeLayout.LayoutParams
        params.marginStart = 0
        sidebarContainer.layoutParams = params
    }

    private fun closeSidebar() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_out)
        sidebarContainer.startAnimation(animation)
        val params = sidebarContainer.layoutParams as RelativeLayout.LayoutParams
        params.marginStart = -sidebarContainer.width
        sidebarContainer.layoutParams = params
    }


    override fun onBackPressed() {
        if (isSidebarOpen) {
            closeSidebar()
            isSidebarOpen = false
        } else {
            super.onBackPressed()
        }
    }

    private fun createOferta() {
        val oferta = Oferta(
            titulo = inputTitulo.text.toString(),
            tipoTrabajo = spinnerTipoOferta.selectedItem.toString(),
            departamento = spinnerDepartamento.selectedItem.toString(),
            descripcion = inputDescripcion.text.toString(),
            profesor = spinnerProfesor.selectedItem.toString(), //De momento no se que hacer aca, pero idealmente hay que hacer que pueda jalar values de la DB
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

            val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val tipoUsuario = sharedPrefs.getString("tipoUsuario", "ESTUDIANTE")

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


