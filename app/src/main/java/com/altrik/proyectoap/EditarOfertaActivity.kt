package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.altrik.proyectoap.utilities.ApiService
import com.altrik.proyectoap.utilities.RetrofitClient
import com.altrik.proyectoap.utilities.request.UpdateOfertaRequest
import kotlinx.coroutines.launch

class EditarOfertaActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var inputTitulo: EditText
    private lateinit var inputTipoTrabajo: EditText
    private lateinit var inputDepartamento: EditText
    private lateinit var inputDescripcion: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar_oferta_trabajo_layout)

        apiService = RetrofitClient.apiService

        inputTitulo = findViewById(R.id.inputTituloOferta)
        inputTipoTrabajo = findViewById(R.id.inputTipoTrabajo)
        inputDepartamento = findViewById(R.id.inputDepartamento)
        inputDescripcion = findViewById(R.id.inputDescripcion)

        val titulo = intent.getStringExtra("titulo")
        val tipoTrabajo = intent.getStringExtra("tipoTrabajo")
        val departamento = intent.getStringExtra("departamento")
        val descripcion = intent.getStringExtra("descripcion")

        inputTitulo.setText(titulo)
        inputTipoTrabajo.setText(tipoTrabajo)
        inputDepartamento.setText(departamento)
        inputDescripcion.setText(descripcion)

        val botonEditarOferta = findViewById<Button>(R.id.botonEditarOferta)
        botonEditarOferta.setOnClickListener {

        }
    }

    private fun editarOferta() {
        val titulo = inputTitulo.text.toString()
        val tipoTrabajo = inputTipoTrabajo.text.toString()
        val departamento = inputDepartamento.text.toString()
        val descripcion = inputDescripcion.text.toString()

        val tituloAnterior = intent.getStringExtra("titulo")
        val tipoTrabajoAnterior = intent.getStringExtra("tipoTrabajo")
        val departamentoAnterior = intent.getStringExtra("departamento")
        val descripcionAnterior = intent.getStringExtra("descripcion")

        val tituloDefinitivo: String? = if (tituloAnterior == titulo) null else titulo
        val tipoTrabajoDefinitivo: String? = if (tipoTrabajoAnterior == tipoTrabajo) null else tipoTrabajo
        val departamentoDefinitivo: String? = if (departamentoAnterior == departamento) null else departamento
        val descripcionDefinitivo: String? = if (descripcionAnterior == descripcion) null else descripcion

        val updateOfertaRequest = UpdateOfertaRequest(
            titulo = tituloDefinitivo,
            tipoTrabajo = tipoTrabajoDefinitivo,
            departamento = departamentoDefinitivo,
            descripcion = descripcionDefinitivo
        )

        lifecycleScope.launch {
            try {
                val response = apiService.updateOferta(
                    titulo = tituloAnterior.toString(),
                    updateOfertaRequest
                )
                if (response.success && response.data != null) {

                    val intent = Intent(this@EditarOfertaActivity, MenuProfessorActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } catch(e: Exception) {

            }
        }
    }
}