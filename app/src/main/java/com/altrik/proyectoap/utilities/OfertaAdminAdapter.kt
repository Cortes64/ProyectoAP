package com.altrik.proyectoap.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.R

class OfertaAdminAdapter(
    private val ofertas: List<Oferta>,
) : RecyclerView.Adapter<OfertaAdminAdapter.OfertaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfertaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.template_admin_oferta, parent, false)
        return OfertaViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfertaViewHolder, position: Int) {
        val oferta = ofertas[position]
        holder.titulo.text = oferta.titulo
        holder.tipoTrabajo.text = oferta.tipoTrabajo
        holder.descripcion.text = oferta.descripcion
        holder.deleteButton.setOnClickListener {
            // Lógica para eliminar la oferta
        }
        holder.editButton.setOnClickListener {
            // Lógica para editar la oferta
        }
        holder.chatButton.setOnClickListener {
            // Lógica para abrir el chat con el profesor
        }
        holder.personButton.setOnClickListener {
            // Lógica para abrir el perfil del profesor
        }
    }

    override fun getItemCount(): Int = ofertas.size

    class OfertaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.textOfertaTitle)
        val tipoTrabajo: TextView = view.findViewById(R.id.textTipoOferta)
        val descripcion: TextView = view.findViewById(R.id.textDescripcionOferta)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_button)
        val editButton: ImageButton = view.findViewById(R.id.edit_button)
        val chatButton: ImageButton = view.findViewById(R.id.chat_button)
        val personButton: ImageButton = view.findViewById(R.id.person_button)
    }
}