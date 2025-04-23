package com.altrik.proyectoap

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.utilities.Historial
import com.altrik.proyectoap.utilities.EstudiantesInteresados
import com.altrik.proyectoap.utilities.EstudiantesInteresadosAdapter
import com.altrik.proyectoap.utilities.FooterBarView
import com.altrik.proyectoap.utilities.HistorialAdapter
import com.altrik.proyectoap.utilities.NavViewHelper
import com.altrik.proyectoap.utilities.Oferta
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RevisarInteresadosActivity: AppCompatActivity() {

    private lateinit var recyclerViewEstudiantes: RecyclerView
    private lateinit var recyclerViewHistorial: RecyclerView

    private lateinit var adapterEstudiantes: EstudiantesInteresadosAdapter
    private lateinit var adapterHistorial: HistorialAdapter

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.revisar_interesados_layout)

        val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val tipoUsuario = sharedPrefs.getString("tipoUsuario", "") ?: "ESTUDIANTE"

        val footer = findViewById<FooterBarView>(R.id.footerBar)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        footer.configurarPara(tipoUsuario)

        NavViewHelper.configurarMenu(navView, tipoUsuario);
        NavViewHelper.configurarListeners(this, navView, tipoUsuario)

        drawerLayout = findViewById(R.id.drawer_layout)
        val imageButtonSidebar = findViewById<ImageButton>(R.id.imageButtonSidebar)
        imageButtonSidebar.setOnClickListener {
            abrirSidebar()
        }

        val ofertaJson = intent.getStringExtra("oferta")
        val oferta = Gson().fromJson(ofertaJson, Oferta::class.java)

        val estudiantes = oferta.estudiantesInteresados
        val historial = oferta.historial

        recyclerViewEstudiantes = findViewById(R.id.recyclerAplicantes)
        recyclerViewHistorial = findViewById(R.id.recyclerHistorial)

        recyclerViewEstudiantes.layoutManager = LinearLayoutManager(this)
        recyclerViewHistorial.layoutManager = LinearLayoutManager(this)

        adapterEstudiantes = EstudiantesInteresadosAdapter(estudiantes)
        adapterHistorial = HistorialAdapter(historial)

        recyclerViewEstudiantes.adapter = adapterEstudiantes
        recyclerViewHistorial.adapter = adapterHistorial

        val textViewTitulo = findViewById<TextView>(R.id.textViewTitle)
        val textViewDescripcion = findViewById<TextView>(R.id.descripcionTrabajo)

        textViewTitulo.text = oferta.titulo
        textViewDescripcion.text = oferta.descripcion


    }

    private fun abrirSidebar() {
        drawerLayout.openDrawer(GravityCompat.START)
    }
}