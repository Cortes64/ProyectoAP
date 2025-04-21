package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.utilities.FooterBarView
import com.altrik.proyectoap.utilities.Oferta
import com.altrik.proyectoap.utilities.OfertaSeguimientoAdapter
import com.altrik.proyectoap.utilities.RetrofitClient
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class SeguimientoActivity: AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OfertaSeguimientoAdapter
    private val listaOferta = mutableListOf<Oferta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seguimiento_layout)

        val nombreUsuario = getSharedPreferences("UserPrefs", MODE_PRIVATE).getString("nombreUsuario", "")

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

        recyclerView = findViewById(R.id.RecyclerViewOferta)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = OfertaSeguimientoAdapter(listaOferta)
        recyclerView.adapter = adapter

        val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val tipoUsuario = sharedPrefs.getString("tipoUsuario", "ESTUDIANTE")

        val footer = findViewById<FooterBarView>(R.id.footerBar)
        footer.configurarPara(tipoUsuario ?: "ESTUDIANTE")

        fetchOfertas()
    }

    private fun fetchOfertas() {
        val correoUsuario = getSharedPreferences("UserPrefs", MODE_PRIVATE).getString("correoUsuario", "")

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getOfertas()

                val ofertasFiltradas = response.filter { oferta ->
                    oferta.estudiantesInteresados.any {
                        it.correoEstudiante == correoUsuario
                    }
                }

                listaOferta.clear()
                listaOferta.addAll(ofertasFiltradas)
                adapter.notifyDataSetChanged()
            } catch(e: Exception) {
                Toast.makeText(this@SeguimientoActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                println("${e.message}")
            }
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