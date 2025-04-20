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
            irMenu()
        }

        val imageButtonMenu = findViewById<ImageButton>(R.id.imageButtonMenu)
        imageButtonMenu.setOnClickListener {
            irSeguimiento()
        }

        val listaOfertaJson = intent.getStringExtra("ofertas")
        val typeOfertas = object : TypeToken<List<Oferta>>() {}.type
        val listaOferta : List<Oferta> = if (listaOfertaJson != null) {
            Gson().fromJson(listaOfertaJson, typeOfertas)
        } else {
            emptyList()
        }

        recyclerView = findViewById(R.id.RecyclerViewOferta)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = OfertaBuscarAdapter(listaOferta)
        recyclerView.adapter = adapter

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
}