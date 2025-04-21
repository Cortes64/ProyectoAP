package com.altrik.proyectoap

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.utilities.Historial
import com.altrik.proyectoap.utilities.EstudiantesInteresados
import com.altrik.proyectoap.utilities.EstudiantesInteresadosAdapter
import com.altrik.proyectoap.utilities.FooterBarView
import com.altrik.proyectoap.utilities.HistorialAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RevisarInteresadosActivity: AppCompatActivity() {

    private lateinit var recyclerViewEstudiantes: RecyclerView
    private lateinit var recyclerViewHistorial: RecyclerView

    private lateinit var adapterEstudiantes: EstudiantesInteresadosAdapter
    private lateinit var adapterHistorial: HistorialAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.revisar_interesados_layout)

        val titulo = intent.getStringExtra("titulo")
        val descripcion = intent.getStringExtra("descripcion")
        val estudiantesJson = intent.getStringExtra("estudiantesJson")
        val historialJson = intent.getStringExtra("historialJson")

        val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val tipoUsuario = sharedPrefs.getString("tipoUsuario", "ESTUDIANTE")

        val footer = findViewById<FooterBarView>(R.id.footerBar)
        footer.configurarPara(tipoUsuario ?: "ESTUDIANTE")

        val typeEstudiantes = object : TypeToken<List<EstudiantesInteresados>>() {}.type
        val estudiantes: List<EstudiantesInteresados> = if (estudiantesJson != null) {
            Gson().fromJson(estudiantesJson, typeEstudiantes)
        } else {
            emptyList()
        }

        val typeHistorial = object: TypeToken<List<Historial>>() {}.type
        val historial: List<Historial> = if (historialJson != null) {
            Gson().fromJson(historialJson, typeHistorial)
        } else {
            emptyList()
        }

        recyclerViewEstudiantes = findViewById(R.id.recyclerAplicantes)
        recyclerViewHistorial = findViewById(R.id.recyclerHistorial)

        adapterEstudiantes = EstudiantesInteresadosAdapter(estudiantes)
        adapterHistorial = HistorialAdapter(historial)

        recyclerViewEstudiantes.adapter = adapterEstudiantes
        recyclerViewHistorial.adapter = adapterHistorial

        val textViewTitulo = findViewById<TextView>(R.id.textViewTitle)
        val textViewDescripcion = findViewById<TextView>(R.id.descripcionTrabajo)

        textViewTitulo.text = titulo
        textViewDescripcion.text = descripcion
    }
}