package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.altrik.proyectoap.utilities.ApiService
import com.altrik.proyectoap.utilities.Beca
import com.altrik.proyectoap.utilities.RetrofitClient
import com.altrik.proyectoap.utilities.response.BecaResponse

class ManejarBeneficioAlumno : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var inputNombre: EditText
    private lateinit var inputDescripcion: EditText
    private lateinit var inputRequisitos: EditText
    private lateinit var inputBeneficios: EditText
    private lateinit var inputProcesoObtencion: EditText
    private lateinit var spinnerTipoBeca: Spinner
    private lateinit var spinnerGrupoBeca: Spinner
    private lateinit var botonCrear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manejar_beneficio_alumno_layout)

        apiService = RetrofitClient.apiService
        inputNombre = findViewById(R.id.InputNombreBeca)
        inputDescripcion = findViewById(R.id.InputDescripcionBeca)
        inputRequisitos = findViewById(R.id.InputRequisitosBeca)
        inputBeneficios = findViewById(R.id.InputBeneficiosBeca)
        inputProcesoObtencion = findViewById(R.id.InputObtencionBeca)
        spinnerTipoBeca = findViewById(R.id.TipoBecaOptions)
        spinnerGrupoBeca = findViewById(R.id.GrupoBecaOptions)
        botonCrear = findViewById(R.id.boton_crear)

        ArrayAdapter.createFromResource(
            this,
            R.array.tipos_beca,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTipoBeca.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.grupos_beca,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerGrupoBeca.adapter = adapter
        }

        spinnerTipoBeca.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem == "CulturalDeportiva") {
                    spinnerGrupoBeca.visibility = View.VISIBLE
                } else {
                    spinnerGrupoBeca.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                spinnerGrupoBeca.visibility = View.GONE
            }
        })

        botonCrear.setOnClickListener {
            crearBeca()
        }
    }

    private fun crearBeca() {
        val nombre = inputNombre.text.toString()
        val descripcion = inputDescripcion.text.toString()
        val requisitos = inputRequisitos.text.toString()
        val beneficios = inputBeneficios.text.toString()
        val procesoObtencion = inputProcesoObtencion.text.toString()
        val tipoBeca = spinnerTipoBeca.selectedItem.toString()
        val grupoBeca = spinnerGrupoBeca.selectedItem.toString()
        val email = intent.getStringExtra("EMAIL").toString()

        val beca = Beca(
            nombre = nombre,
            descripcion = descripcion,
            requisitos = requisitos,
            procesoObtencion = procesoObtencion,
            tipo = tipoBeca,
            grupo = grupoBeca,
            estudiante = email,
            beneficios = beneficios
        )

        val call = apiService.createBeca(beca)
        call.enqueue(object : retrofit2.Callback<BecaResponse> {
            override fun onResponse(
                call: retrofit2.Call<BecaResponse>,
                response: retrofit2.Response<BecaResponse>
            ) {
                if (response.isSuccessful) {
                    val mensaje = response.body()?.message ?: "Beca creada exitosamente"
                    Toast.makeText(this@ManejarBeneficioAlumno, mensaje, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ManejarBeneficioAlumno, ManageStudentActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@ManejarBeneficioAlumno, "Error al crear la beca", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<BecaResponse>, t: Throwable) {
                Toast.makeText(this@ManejarBeneficioAlumno, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}