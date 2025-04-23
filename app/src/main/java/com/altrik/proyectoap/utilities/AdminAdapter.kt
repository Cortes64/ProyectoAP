package com.altrik.proyectoap.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminAdapter(
    private val ofertas: List<Oferta>,
    private val reportes: List<Reporte>,
    private val onDelete: suspend (Oferta) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_OFERTA = 1
        private const val TYPE_REPORTE = 2
    }

    override fun getItemCount(): Int = ofertas.size + reportes.size

    override fun getItemViewType(position: Int): Int {
        return if (position < ofertas.size) { TYPE_OFERTA } else { TYPE_REPORTE }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_OFERTA -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.template_admin_oferta, parent, false)
                OfertaViewHolder(view)
            }
            TYPE_REPORTE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.template_reporte, parent, false)
                ReporteViewHolder(view)
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OfertaViewHolder -> {
                val oferta = ofertas[position]
                holder.apply {
                    titulo.text = oferta.titulo
                    tipoTrabajo.text = oferta.tipoTrabajo
                    descripcion.text = oferta.descripcion
                    deleteButton.setOnClickListener {
                        val oferta = ofertas[position]
                        CoroutineScope(Dispatchers.IO).launch {
                            onDelete(oferta)
                        }
                    }
                    editButton.setOnClickListener {
                        // Lógica para editar la oferta
                    }
                    chatButton.setOnClickListener {
                        // Lógica para abrir el chat con el profesor
                    }
                    personButton.setOnClickListener {
                        // Lógica para abrir el perfil del profesor
                    }
                }
            }
            is ReporteViewHolder -> {
                val reporte = reportes[position]
                holder.apply {
                    tituloReporte.text = reporte.titulo
                    descripcionReporte.text = reporte.descripcion
                }
            }
        }
    }

    class OfertaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.textOfertaTitle)
        val tipoTrabajo: TextView = view.findViewById(R.id.textTipoOferta)
        val descripcion: TextView = view.findViewById(R.id.textDescripcionOferta)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_button)
        val editButton: ImageButton = view.findViewById(R.id.edit_button)
        val chatButton: ImageButton = view.findViewById(R.id.chat_button)
        val personButton: ImageButton = view.findViewById(R.id.person_button)
    }

    class ReporteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tituloReporte: TextView = view.findViewById(R.id.TextTituloInforme)
        val descripcionReporte : TextView = view.findViewById(R.id.TextResumenInforme)
    }
}