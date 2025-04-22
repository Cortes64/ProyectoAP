package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.utilities.Oferta
import com.altrik.proyectoap.utilities.AdminAdapter
import com.altrik.proyectoap.utilities.Reporte
import com.google.android.material.navigation.NavigationView
import androidx.lifecycle.lifecycleScope
import com.altrik.proyectoap.utilities.FooterBarView
import com.altrik.proyectoap.utilities.NavViewHelper
import com.altrik.proyectoap.utilities.RetrofitClient
import kotlinx.coroutines.launch

class MenuAdminActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdminAdapter
    private val listaOferta = mutableListOf<Oferta>()
    private val listaReportes = mutableListOf<Reporte>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_admin_layout)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val imageButtonSidebar = findViewById<ImageButton>(R.id.imageButtonSidebar)
        imageButtonSidebar.setOnClickListener {
            abrirSidebar()
        }

        recyclerView = findViewById(R.id.RecyclerViewOferta)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdminAdapter(listaOferta, listaReportes)
        recyclerView.adapter = adapter

        val footer = findViewById<FooterBarView>(R.id.footerBar)
        footer.configurarPara("ADMINISTRADOR")

        val tipoUsuario = sharedPreferences.getString("tipoUsuario", "") ?: "ESTUDIANTE"
        NavViewHelper.configurarMenu(navView, tipoUsuario);
        NavViewHelper.configurarListeners(this, navView, tipoUsuario)

        fetchOfertasReportes()
    }

    private fun fetchOfertasReportes() {
        lifecycleScope.launch {
            try {
                val responseOfertas = RetrofitClient.apiService.getOfertas()
                val responseReporte = RetrofitClient.apiService.getReportes()

                listaOferta.clear()
                listaOferta.addAll(responseOfertas)
                listaReportes.clear()
                listaReportes.addAll(responseReporte)

                adapter.notifyDataSetChanged()

            } catch (err: Exception) {
                Toast.makeText(this@MenuAdminActivity, "Error: ${err.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun abrirSidebar() {
        drawerLayout.openDrawer(GravityCompat.START)
    }
}