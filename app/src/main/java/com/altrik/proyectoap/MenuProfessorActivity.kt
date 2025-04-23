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
import com.altrik.proyectoap.utilities.FooterBarView
import com.altrik.proyectoap.utilities.NavViewHelper
import com.altrik.proyectoap.utilities.Oferta
import com.altrik.proyectoap.utilities.RetrofitClient
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val imageButtonSidebar = findViewById<ImageButton>(R.id.imageButtonSidebar)
        imageButtonSidebar.setOnClickListener {
            abrirSidebar()
        }

        recyclerView = findViewById(R.id.RecyclerViewOferta)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Al final se puede usar el mismo AdminAdapter, son las mismas opciones,
        // solo es cuestión de que recibe cosas diferentes en fetchOfertas

        val onDeleteOferta: suspend (Oferta) -> Unit = { oferta ->
            try {
                RetrofitClient.apiService.deleteOferta(oferta.titulo)
                withContext(Dispatchers.Main) {
                    listaOferta.remove(oferta)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this@MenuProfessorActivity, "Oferta eliminada", Toast.LENGTH_SHORT).show()
                }
            } catch (err: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MenuProfessorActivity, "Error: ${err.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        adapter = AdminAdapter(
            context = this,
            ofertas = listaOferta,
            reportes = emptyList(),
            onDelete = onDeleteOferta
        )
        recyclerView.adapter = adapter

        val footer = findViewById<FooterBarView>(R.id.footerBar)
        val tipoUsuario = sharedPreferences.getString("tipoUsuario", "") ?: "ESTUDIANTE"
        footer.configurarPara(tipoUsuario)
        NavViewHelper.configurarMenu(navView, tipoUsuario);
        NavViewHelper.configurarListeners(this, navView, tipoUsuario)

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
}