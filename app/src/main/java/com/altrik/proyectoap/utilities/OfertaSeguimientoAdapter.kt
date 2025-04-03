package com.altrik.proyectoap.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.R

class OfertaSeguimientoAdapter (
    private val ofertas: List<Oferta>
): RecyclerView.Adapter<OfertaSeguimientoAdapter.OfertaViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OfertaSeguimientoAdapter.OfertaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.template_seguimiento_oferta, parent, false)
        return OfertaViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfertaViewHolder, position: Int) {
        val oferta = ofertas[position]
        holder.titulo.text = oferta.titulo
        holder.tipoTrabajo.text = oferta.tipoTrabajo
        holder.descripcion.text = oferta.descripcion
        holder.adButton.setOnClickListener {
            // Lógica para abrir la oferta
        }
        holder.notificationsButton.setOnClickListener {
            // Lógica para abrir el perfil del profesor
        }
    }

    override fun getItemCount(): Int = ofertas.size

    class OfertaViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.textOfertaTitle)
        val tipoTrabajo: TextView = view.findViewById(R.id.textTipoOferta)
        val descripcion: TextView = view.findViewById(R.id.textDescripcionOferta)
        val adButton: ImageButton = view.findViewById(R.id.ad_button)
        val notificationsButton: ImageButton = view.findViewById(R.id.notifications_button)
    }
}