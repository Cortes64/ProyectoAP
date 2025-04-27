package com.altrik.proyectoap.utilities

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.EvaluarAlumnoActivity
import com.altrik.proyectoap.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EstudiantesInteresadosAdapter (
    private val estudiantesInteresados: MutableList<EstudiantesInteresados>,
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
        holder.aceptado.text = if (estudianteInteresado.aceptado) "Aceptado" else "Rechazado"
        holder.promedio.text = estudianteInteresado.promedioPonderado

        if (estudianteInteresado.aceptado) {
            holder.star.visibility = View.VISIBLE
            holder.check.isEnabled = false
            holder.check.alpha = 0.5f
        } else {
            holder.star.visibility = View.GONE
            holder.check.isEnabled = true
            holder.check.alpha = 1.0f
        }

        holder.star.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, EvaluarAlumnoActivity::class.java)

            val sharedPrefs = context.getSharedPreferences("UserPrefs", MODE_PRIVATE)
            intent.putExtra("correoEstudiante", estudianteInteresado.correoEstudiante )
            intent.putExtra("email", sharedPrefs.getString("correoUsuario", "") )
            context.startActivity(intent)
        }

        holder.check.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitClient.apiService.updateEstudianteInteresado(
                        titulo = tituloOferta,
                        correo = estudianteInteresado.correoEstudiante,
                    )

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            estudianteInteresado.aceptado = true
                            notifyItemChanged(position)

                            Toast.makeText(holder.itemView.context, "Estudiante aceptado!", Toast.LENGTH_SHORT).show()
                        } else {
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
                            estudiantesInteresados.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, estudiantesInteresados.size)

                            Toast.makeText(holder.itemView.context, "Estudiante rechazado y eliminado", Toast.LENGTH_SHORT).show()
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
        val aceptado: TextView = view.findViewById(R.id.student_list_aceptado)
        val star: ImageButton = view.findViewById(R.id.evaluarButton)
        val check: ImageButton = view.findViewById<ImageButton>(R.id.button_check)
        val close: ImageButton = view.findViewById<ImageButton>(R.id.button_close)
    }
}