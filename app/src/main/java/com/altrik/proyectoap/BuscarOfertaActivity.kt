package com.altrik.proyectoap

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.altrik.proyectoap.utilities.NavViewHelper
import com.altrik.proyectoap.utilities.Oferta
import com.altrik.proyectoap.utilities.OfertaBuscarAdapter
import com.altrik.proyectoap.utilities.RetrofitClient
import com.altrik.proyectoap.utilities.Usuario
import com.altrik.proyectoap.utilities.UsuarioAdapter
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class BuscarOfertaActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OfertaBuscarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buscar_oferta_layout)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        val headerView = navView.getHeaderView(0)
        val sidebarNombre = headerView.findViewById<TextView>(R.id.sidebarNombre)

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val nombreUsuario = sharedPreferences.getString("nombreUsuario", "")
        sidebarNombre.text = nombreUsuario

        val imageButtonSidebar = findViewById<ImageButton>(R.id.imageButtonSidebar)
        imageButtonSidebar.setOnClickListener {
            abrirSidebar()
        }

        val tipoUsuario = sharedPreferences.getString("tipoUsuario", "") ?: "ESTUDIANTE"

        val footer = findViewById<FooterBarView>(R.id.footerBar)
        footer.configurarPara(tipoUsuario);
        NavViewHelper.configurarMenu(navView, tipoUsuario);
        NavViewHelper.configurarListeners(this, navView, tipoUsuario)

        val listaOfertaJson = intent.getStringExtra("ofertas")
        val typeOfertas = object : TypeToken<List<Oferta>>() {}.type
        val listaOferta : List<Oferta> = if (listaOfertaJson != null) {
            Gson().fromJson(listaOfertaJson, typeOfertas)
        } else {
            emptyList()
        }

        recyclerView = findViewById(R.id.RecyclerViewOferta)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = OfertaBuscarAdapter(
            ofertas = listaOferta,
            nombreUsuario = sharedPreferences.getString("nombreUsuario", "") ?: "",
            correoUsuario = sharedPreferences.getString("correoUsuario", "") ?: "",
            promedioPonderado = sharedPreferences.getString("promedioPonderadoUsuario", "") ?: ""
        )
        recyclerView.adapter = adapter

    }

    private fun abrirSidebar() {
        drawerLayout.openDrawer(GravityCompat.START)
    }
}