package com.altrik.proyectoap.utilities


import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.R
import com.altrik.proyectoap.RevisarInteresadosActivity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfertaBuscarAdapter (
    private val ofertas: List<Oferta>,
    private val nombreUsuario: String,
    private val correoUsuario: String,
    private val promedioPonderado: String
): RecyclerView.Adapter<OfertaBuscarAdapter.OfertaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfertaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.template_buscar_oferta, parent, false)
        return OfertaViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfertaViewHolder, position: Int) {
        val oferta = ofertas[position]
        holder.titulo.text = oferta.titulo
        holder.tipoTrabajo.text = oferta.tipoTrabajo
        holder.descripcion.text = oferta.descripcion

        val context = holder.itemView.context


        holder.addButton.setOnClickListener {
            val nuevoEstudiante = EstudiantesInteresados(
                nombre = nombreUsuario,
                correoEstudiante = correoUsuario,
                fechaRegistro = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                aceptado = false,
                promedioPonderado = promedioPonderado
            )

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitClient.apiService.addEstudianteInteresado(oferta.titulo, nuevoEstudiante)

                    withContext(Dispatchers.Main) {
                        if (response.success) {
                            oferta.estudiantesInteresados.add(response.estudianteInteresado)
                            notifyItemChanged(position)

                            Toast.makeText(context, "Estudiante agregado correctamente!", Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(context, "Error del servidor: ${response.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
                        Log.e("ADD_ESTUDIANTE", "Error al agregar estudiante", e)
                    }
                }
            }
        }
    }


    override fun getItemCount(): Int = ofertas.size

    class OfertaViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.textOfertaTitle)
        val tipoTrabajo: TextView = view.findViewById(R.id.textTipoOferta)
        val descripcion: TextView = view.findViewById(R.id.textDescripcionOferta)
        val addButton: ImageButton = view.findViewById(R.id.add_button)
    }
}