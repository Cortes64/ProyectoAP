package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MenuStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_student_layout)

        val btnMenu = findViewById<ImageButton>(R.id.imageButtonMenu)
        btnMenu.setOnClickListener {
            openMenu()
        }

        val btnDollar = findViewById<ImageButton>(R.id.imageButtonDollar)
        btnDollar.setOnClickListener {
            // TODO
        }

        val btnEdit = findViewById<ImageButton>(R.id.imageButtonEdit)
        btnEdit.setOnClickListener {
            openEditProfile()
        }
    }

    private fun openMenu() {
        val intent = Intent(this, MenuStudentActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openEditProfile() {
        val intent = Intent(this, EditProfileStudentActivity::class.java)
        startActivity(intent)
        finish()
    }
}