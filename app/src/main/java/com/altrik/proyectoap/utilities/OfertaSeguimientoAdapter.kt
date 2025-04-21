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

class OfertaSeguimientoAdapter (
    private val ofertas: MutableList<Oferta>,
    private val correoUsuario: String
): RecyclerView.Adapter<OfertaSeguimientoAdapter.OfertaViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OfertaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.template_seguimiento_oferta, parent, false)
        return OfertaViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfertaViewHolder, position: Int) {
        val oferta = ofertas[position]
        holder.titulo.text = oferta.titulo
        holder.tipoTrabajo.text = oferta.tipoTrabajo
        holder.descripcion.text = oferta.descripcion
        holder.removeButton.setOnClickListener {
            val context = holder.itemView.context

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitClient.apiService.removeEstudianteInteresado(
                        oferta.titulo,
                        correoUsuario
                    )

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            ofertas.removeAt(position)
                            notifyItemRemoved(position)
                        } else {
                            Toast.makeText(context, "Error al eliminar el estudiante interesado", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
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
        val removeButton: ImageButton = view.findViewById(R.id.remove_button)
    }
}