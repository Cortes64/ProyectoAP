package com.altrik.proyectoap

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.altrik.proyectoap.utilities.ApiService
import com.altrik.proyectoap.utilities.RetrofitClient

class ManejarBeneficioAlumno : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var inputNombre: EditText
    private lateinit var inputDescripcion: EditText
    private lateinit var inputRequisitos: EditText
    private lateinit var inputProcesoObtencion: EditText
    private lateinit var spinnerTipoBeca: Spinner
    private lateinit var spinnerGrupoBeca: Spinner
    private lateinit var botonCrear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_student_layout)

        apiService = RetrofitClient.apiService
        inputNombre = findViewById(R.id.InputNombreBeca)
        inputDescripcion = findViewById(R.id.InputDescripcionBeca)
        inputRequisitos = findViewById(R.id.InputRequisitosBeca)
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
    }
}