package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class StatusFinancieroActivity: AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private val nombreUsuario = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        .getString("nombreUsuario", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.status_financiero_layout)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        val headerView = navView.getHeaderView(0)
        val sidebarNombre = headerView.findViewById<TextView>(R.id.sidebarNombre)

        sidebarNombre.text = nombreUsuario

        val imageButtonSidebar = findViewById<ImageButton>(R.id.imageButtonSidebar)
        imageButtonSidebar.setOnClickListener {
            abrirSidebar()
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_inicio -> {
                    irMenu()
                    true
                }
                R.id.nav_buscar_oferta -> {
                    irBuscarOferta()
                    true
                }
                R.id.nav_seguimiento -> {
                    irSeguimiento()
                    true
                }
                R.id.nav_cerrar_sesion -> {
                    cerrarSesion()
                    true
                }
                else -> false
            }
        }

        val imageButtonEdit = findViewById<ImageButton>(R.id.imageButtonEdit)
        imageButtonEdit.setOnClickListener {
            irEditarPerfil()
        }

        val imageButtonDollar = findViewById<ImageButton>(R.id.imageButtonDollar)
        imageButtonDollar.setOnClickListener {
            irStatusFinanciero()
        }

        val imageButtonMenu = findViewById<ImageButton>(R.id.imageButtonMenu)
        imageButtonMenu.setOnClickListener {
            irSeguimiento()
        }
    }

    private fun abrirSidebar() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun cerrarSesion() {
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit()
        sharedPref.clear()
        sharedPref.apply()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irMenu() {
        val intent = Intent(this, MenuStudentActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irBuscarOferta() {
        val intent = Intent(this, FormBuscarOfertaActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irSeguimiento() {
        val intent = Intent(this, SeguimientoActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irEditarPerfil() {
        val intent = Intent(this, EditProfileStudentActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irStatusFinanciero() {
        val intent = Intent(this, StatusFinancieroActivity::class.java)
        startActivity(intent)
        finish()
    }
}