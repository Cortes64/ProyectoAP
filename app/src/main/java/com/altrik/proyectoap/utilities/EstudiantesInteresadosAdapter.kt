package com.altrik.proyectoap.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.R

class EstudiantesInteresadosAdapter (
    private val estudiantesInteresados: List<EstudiantesInteresados>
) : RecyclerView.Adapter<EstudiantesInteresadosAdapter.EstudiantesInteresadosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstudiantesInteresadosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.template_estudiante_interesado, parent, false)
        return EstudiantesInteresadosViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstudiantesInteresadosViewHolder, position: Int) {
        val estudianteInteresado = estudiantesInteresados[position]
        holder.email.text = estudianteInteresado.correoEstudiante
        holder.tipoUsuario.text = "ESTUDIANTE"
    }

    override fun getItemCount(): Int = estudiantesInteresados.size

    class EstudiantesInteresadosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val promedio: TextView = view.findViewById(R.id.student_promedio_ponderado)
        val email: TextView = view.findViewById(R.id.student_list_correo)
        val tipoUsuario: TextView = view.findViewById(R.id.student_list_tipo)
    }
}