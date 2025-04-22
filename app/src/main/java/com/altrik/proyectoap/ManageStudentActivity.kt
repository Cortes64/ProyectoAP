package com.altrik.proyectoap

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.altrik.proyectoap.utilities.FooterBarView
import com.altrik.proyectoap.utilities.RetrofitClient
import kotlinx.coroutines.launch

class ManageStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_student_layout)

        val email = intent.getStringExtra("EMAIL")

        val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val tipoUsuario = sharedPrefs.getString("tipoUsuario", "ESTUDIANTE")

        val footer = findViewById<FooterBarView>(R.id.footerBar)
        footer.configurarPara(tipoUsuario ?: "ESTUDIANTE")

        llenarCampos()
    }

    private fun llenarCampos() {
        val email = intent.getStringExtra("EMAIL")

        if (email != null) {
            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.apiService.getPerfilEstudianteSimple(email)

                    if (response.success && response.data != null) {
                        val beca = response.data.beca
                        val oferta = response.data.oferta

                        // Llenar la UI
                        findViewById<TextView>(R.id.textViewTipoBeca).text =
                            beca?.tipo ?: "Sin beca registrada"
                        findViewById<TextView>(R.id.textViewEncargadoBeca).text =
                            beca?.nombre ?: "-"

                        findViewById<TextView>(R.id.textViewEscuelaSolicitud).text =
                            oferta?.departamento ?: "-"
                        findViewById<TextView>(R.id.textViewNombreSolicitud).text =
                            oferta?.profesor ?: "-"
                        findViewById<TextView>(R.id.textViewTipoTrabajoSolicitud).text =
                            oferta?.tipoTrabajo ?: "-"
                    } else {
                        Toast.makeText(this@ManageStudentActivity, "Datos no encontrados", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@ManageStudentActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}