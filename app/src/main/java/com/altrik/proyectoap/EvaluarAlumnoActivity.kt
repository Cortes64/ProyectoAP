package com.altrik.proyectoap

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.altrik.proyectoap.utilities.FooterBarView
import com.altrik.proyectoap.utilities.MailSender
import com.altrik.proyectoap.utilities.NavViewHelper
import com.altrik.proyectoap.utilities.request.EvaluacionRequest
import com.google.android.material.navigation.NavigationView

class EvaluarAlumnoActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private var Calificacionfinal = 0
    private lateinit var estrellasrating: List<ImageView>
    private lateinit var inputComentarios: EditText
    private lateinit var botonCalificar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.evaluar_alumno_layout)  // Updated layout reference

        val navView = findViewById<NavigationView>(R.id.nav_view)
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val tipoUsuario = sharedPreferences.getString("tipoUsuario", "") ?: "ESTUDIANTE"

        val footer = findViewById<FooterBarView>(R.id.footerBar)
        footer.configurarPara(tipoUsuario);
        NavViewHelper.configurarMenu(navView, tipoUsuario);
        NavViewHelper.configurarListeners(this, navView, tipoUsuario)

        inicializarpantalla()

        val imageButtonSidebar = findViewById<ImageButton>(R.id.imageButtonSidebar)
        imageButtonSidebar.setOnClickListener {
            abrirSidebar()
        }

        estrellitas()
        setupSubmitButton()

    }

    private fun abrirSidebar() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun inicializarpantalla() {
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        estrellasrating = listOf(
            findViewById(R.id.star1),
            findViewById(R.id.star2),
            findViewById(R.id.star3),
            findViewById(R.id.star4),
            findViewById(R.id.star5)
        )
        inputComentarios = findViewById(R.id.inputComentarios)
        botonCalificar = findViewById(R.id.botonCalificar)
    }

    private fun estrellitas() {
        estrellasrating.forEachIndexed { index, estrella ->
            estrella.setOnClickListener {
                Calificacionfinal = index + 1
                ajustarestrellitas()
            }
        }
    }

    private fun ajustarestrellitas() {
        estrellasrating.forEachIndexed { index, estrella ->
            val llena = index < Calificacionfinal
            estrella.isActivated = llena
            estrella.contentDescription = if (llena) "estrellas dadas ${index + 1}"
            else "estrellas no dadas ${index + 1}"
        }
    }

    private fun setupSubmitButton() {
        botonCalificar.setOnClickListener {
            when {
                Calificacionfinal == 0 -> showError("Seleccione una puntuación")
                inputComentarios.text.isEmpty() -> showError("Ingrese comentarios")
                else -> submitEvaluation()
            }
        }
    }

    private fun submitEvaluation() {
        val correoEstudiante = intent.getStringExtra("correoEstudiante")
        val correo = intent.getStringExtra("email") ?: run {
            // En TEORIA esto deberia de poder agarrar el correo del login
            showError("No se encontro correo al cual enviar esto")
            return
        }

        val comentarios = inputComentarios.text.toString()

        val asunto = "Evaluacion de trabajo"
        val cuerpo = """
            Alumno: $correoEstudiante
            Calificacion de alumno: $Calificacionfinal / 5
            Comentarios adicionales:
            $comentarios
        """.trimIndent()

        try {
            MailSender.sendEmail(
                context = this,
                to = correo,
                subject = asunto,
                body = cuerpo
            )

            Calificacionfinal = 0
            inputComentarios.text.clear()
            ajustarestrellitas()
            Toast.makeText(this, "Evaluación enviada con éxito", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            showError("Error al enviar la evaluación: ${e.localizedMessage}")
        }

    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}