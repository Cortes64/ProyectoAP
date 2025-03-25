package com.altrik.proyectoap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ManageStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_student_layout)

        val nombre = intent.getStringExtra("NOMBRE")
        val apellidos = intent.getStringExtra("APELLIDOS")
        val email = intent.getStringExtra("EMAIL")
        val tipo = intent.getStringExtra("TIPO")
    }
}