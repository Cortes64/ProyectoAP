package com.altrik.proyectoap.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EstudiantesInteresadosAdapter (
    private val estudiantesInteresados: List<EstudiantesInteresados>,
    private val tituloOferta: String
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
        holder.promedio.text = estudianteInteresado.promedioPonderado.toString()

        holder.check.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitClient.apiService.updateEstudianteInteresado(
                        titulo = tituloOferta,
                        correo = estudianteInteresado.correoEstudiante,
                    )

                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(holder.itemView.context, "Estudiante aceptado!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(holder.itemView.context, "Error al aceptar estudiante", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(holder.itemView.context, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        holder.close.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitClient.apiService.removeEstudianteInteresado(
                        titulo = tituloOferta,
                        correo = estudianteInteresado.correoEstudiante,
                    )

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Toast.makeText(holder.itemView.context, "Estudiante rechazado!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(holder.itemView.context, "Error al rechazar estudiante", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(holder.itemView.context, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = estudiantesInteresados.size

    class EstudiantesInteresadosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val promedio: TextView = view.findViewById(R.id.student_promedio_ponderado)
        val email: TextView = view.findViewById(R.id.student_list_correo)
        val tipoUsuario: TextView = view.findViewById(R.id.student_list_tipo)

        val check: ImageButton = view.findViewById<ImageButton>(R.id.button_check)
        val close: ImageButton = view.findViewById<ImageButton>(R.id.button_close)
    }
}