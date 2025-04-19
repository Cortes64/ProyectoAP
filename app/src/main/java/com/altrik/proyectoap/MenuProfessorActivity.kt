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
import com.altrik.proyectoap.utilities.AdminAdapter
import com.altrik.proyectoap.utilities.Oferta
import com.altrik.proyectoap.utilities.RetrofitClient
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch

class MenuProfessorActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdminAdapter
    private val listaOferta = mutableListOf<Oferta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_professor_layout)

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
                R.id.nav_gestionar_usuarios -> {
                    irGestionarUsuarios()
                    true
                }
                R.id.nav_buscar_publicaciones -> {
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
        val imageFolderPlus = findViewById<ImageButton>(R.id.imageFolderPlus)
        val imageFolderCheck = findViewById<ImageButton>(R.id.imageFolderCheck)

        imageButtonEdit.setOnClickListener {
            val intent = Intent(this, EditProfileStudentActivity::class.java)
            // Se debe hacer y cambiar por el EditProfileProfessorActivity.
            startActivity(intent)
            finish()
        }

        imageFolderPlus.setOnClickListener {
            val intent = Intent(this, CrearOfertaActivity::class.java)
            startActivity(intent)
            finish()
        }

        imageFolderCheck.setOnClickListener {
            val intent = Intent(this, MenuProfessorActivity::class.java)
            startActivity(intent)
            finish()
        }

        recyclerView = findViewById(R.id.RecyclerViewOferta)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Al final se puede usar el mismo AdminAdapter, son las mismas opciones,
        // solo es cuestión de que recibe cosas diferentes en fetchOfertas
        adapter = AdminAdapter(listaOferta, emptyList())
        recyclerView.adapter = adapter

        fetchOfertas()
    }

    private fun fetchOfertas() {
        lifecycleScope.launch {
            try {
                // Se puede hacer un getOfertasSchool, para que sea especifico de cada escuela.
                val responseOfertas = RetrofitClient.apiService.getOfertas()
                listaOferta.clear()
                listaOferta.addAll(responseOfertas)
                adapter.notifyDataSetChanged()
            } catch(err: Exception) {
                Toast.makeText(this@MenuProfessorActivity, "Error: ${err.message}", Toast.LENGTH_SHORT).show()
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
        val intent = Intent(this, MenuProfessorActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irGestionarUsuarios() {
        val intent = Intent(this, FormBuscarEstudianteActivity::class.java)
        startActivity(intent)
        finish()
    }
}