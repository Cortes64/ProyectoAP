package com.altrik.proyectoap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.altrik.proyectoap.utilities.FooterBarView

class ManageStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_student_layout)

        val nombre = intent.getStringExtra("NOMBRE")
        val apellidos = intent.getStringExtra("APELLIDOS")
        val email = intent.getStringExtra("EMAIL")
        val tipo = intent.getStringExtra("TIPO")

        val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val tipoUsuario = sharedPrefs.getString("tipoUsuario", "ESTUDIANTE")

        val footer = findViewById<FooterBarView>(R.id.footerBar)
        footer.configurarPara(tipoUsuario ?: "ESTUDIANTE")
    }
}