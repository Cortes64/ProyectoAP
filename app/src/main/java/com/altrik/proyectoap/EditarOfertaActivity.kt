package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.altrik.proyectoap.utilities.ApiService
import com.altrik.proyectoap.utilities.FooterBarView
import com.altrik.proyectoap.utilities.NavViewHelper
import com.altrik.proyectoap.utilities.RetrofitClient
import com.altrik.proyectoap.utilities.request.AddHistorialRequest
import com.altrik.proyectoap.utilities.request.UpdateOfertaRequest
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class EditarOfertaActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var apiService: ApiService
    private lateinit var inputTitulo: EditText
    private lateinit var inputTipoTrabajo: EditText
    private lateinit var inputDepartamento: EditText
    private lateinit var inputDescripcion: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar_oferta_trabajo_layout)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val tipoUsuario = sharedPreferences.getString("tipoUsuario", "") ?: "ESTUDIANTE"

        val imageButtonSidebar = findViewById<ImageButton>(R.id.imageButtonSidebar)
        imageButtonSidebar.setOnClickListener {
            abrirSidebar()
        }

        val footer = findViewById<FooterBarView>(R.id.footerBar)
        footer.configurarPara(tipoUsuario);
        NavViewHelper.configurarMenu(navView, tipoUsuario);
        NavViewHelper.configurarListeners(this, navView, tipoUsuario)

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
            editarOferta()
        }
    }

    private fun abrirSidebar() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun editarOferta() {
        val titulo = inputTitulo.text.toString()
        val tipoTrabajo = inputTipoTrabajo.text.toString()
        val departamento = inputDepartamento.text.toString()
        val descripcion = inputDescripcion.text.toString()

        if (titulo.isBlank() && tipoTrabajo.isBlank() && departamento.isBlank() && descripcion.isBlank()) {
            Toast.makeText(this, "Al menos un campo debe estar completado", Toast.LENGTH_SHORT).show()
            return
        }

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
                    val cambios = listOfNotNull(
                        if (tituloDefinitivo != null) AddHistorialRequest("Título", tituloAnterior ?: "", titulo) else null,
                        if (tipoTrabajoDefinitivo  != null) AddHistorialRequest("Tipo de trabajo", tipoTrabajoAnterior ?: "", tipoTrabajo) else null,
                        if (departamentoDefinitivo  != null) AddHistorialRequest("Departamento", departamentoAnterior ?: "", departamento) else null,
                        if (descripcionDefinitivo  != null) AddHistorialRequest("Descripción", descripcionAnterior ?: "", descripcion) else null
                    )

                    for (cambio in cambios) {
                        try {
                            apiService.addToHistorial(tituloAnterior ?: "", cambio)
                        } catch (e: Exception) {
                            Toast.makeText(this@EditarOfertaActivity, "Error al agregar cambio al historial: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }

                    Toast.makeText(this@EditarOfertaActivity, "Oferta actualizada con éxito", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@EditarOfertaActivity, MenuProfessorActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } catch(e: Exception) {
                Toast.makeText(this@EditarOfertaActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}