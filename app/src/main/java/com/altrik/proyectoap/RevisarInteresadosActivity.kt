package com.altrik.proyectoap

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.utilities.Historial
import com.altrik.proyectoap.utilities.EstudiantesInteresados
import com.altrik.proyectoap.utilities.EstudiantesInteresadosAdapter
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

        val typeEstudiantes = object : TypeToken<List<EstudiantesInteresados>>() {}.type
        val estudiantes: List<EstudiantesInteresados> = Gson().fromJson(estudiantesJson, typeEstudiantes)

        val typeHistorial = object: TypeToken<List<Historial>>() {}.type
        val historial: List<Historial> = Gson().fromJson(historialJson, typeHistorial)

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