package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.altrik.proyectoap.utilities.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import com.altrik.proyectoap.utilities.Oferta

class FormBuscarOfertaActivity : AppCompatActivity() {

    private lateinit var inputTitulo: EditText
    private lateinit var inputRequisitos: EditText
    private lateinit var departamentoSpinner: Spinner
    private lateinit var botonBuscar: Button

    private var departamentoSeleccionado: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_buscar_oferta)

        inputTitulo = findViewById(R.id.inputTituloOferta)
        inputRequisitos = findViewById(R.id.inputRequisitos)
        departamentoSpinner = findViewById(R.id.departamentoOptions)
        botonBuscar = findViewById(R.id.boton_buscar)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.array_lugares_trabajo,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        departamentoSpinner.adapter = adapter

        departamentoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View, position: Int, id: Long
            ) {
                departamentoSeleccionado = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                departamentoSeleccionado = null
            }
        }

        botonBuscar.setOnClickListener {
            buscarOfertas()
        }
    }

    private fun buscarOfertas() {
        val titulo = inputTitulo.text.toString().ifBlank { null }
        val requisitos = inputRequisitos.text.toString().ifBlank { null }
        val departamento = departamentoSeleccionado

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.filtrarOfertas(
                    titulo = titulo,
                    departamento = departamento,
                    requisitos = requisitos
                )

                val listaOfertas = response.data ?: emptyList()

                for (oferta in listaOfertas) {
                    Log.d("OFERTA", "Titulo: ${oferta.titulo} | Departamento: ${oferta.departamento}")
                }

                val intent = Intent(this@FormBuscarOfertaActivity, BuscarOfertaActivity::class.java)

                val gson = Gson()
                val ofertasJson = gson.toJson(listaOfertas)

                intent.putExtra("ofertas", ofertasJson)

                startActivity(intent)
                finish()

            } catch (e: IOException) {
                Toast.makeText(this@FormBuscarOfertaActivity, "Sin conexion", Toast.LENGTH_SHORT).show()
            } catch (e: HttpException) {
                Toast.makeText(this@FormBuscarOfertaActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}