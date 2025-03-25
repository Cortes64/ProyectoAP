package com.altrik.proyectoap.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.R

class OfertaAdapter(
    private val ofertas: List<OfertaAcademica>,
) : RecyclerView.Adapter<OfertaAdapter.OfertaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfertaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.template_oferta_estudiante, parent, false)
        return OfertaViewHolder(view)
    }

    override fun onBindViewHoler(holder: OfertaViewHolder, position: Int) {
        val oferta = ofertas[position]
        holder.titulo.text = oferta.titulo
        holder.tipoTrabajo.text = oferta.tipoTrabajo
        holder.descripcion.text = oferta.descripcion
    }

    override fun getItemCount(): Int = ofertas.size

    class OfertaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.textOfertaTitle)
        val tipoTrabajo: TextView = view.findViewById(R.id.textTipoOferta)
        val descripcion: TextView = view.findViewById(R.id.textDescripcionOferta)
    }
}