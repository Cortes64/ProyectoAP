package com.altrik.proyectoap.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.R

class HistorialAdapter (
    private val historial: List<Historial>
) : RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.template_historial, parent, false)
        return HistorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
        val elemento = historial[position]
        holder.numeroCambio.text = "Cambio #${position + 1}"
        holder.campoModificado.text = "Campo modificado: ${elemento.campoModificado}"
        holder.valorAnterior.text = "Valor anterior: ${elemento.valorAnterior}"
        holder.valorNuevo.text = "Valor nuevo: ${elemento.valorNuevo}"
        holder.fechaCambio.text = "Fecha cambio: ${elemento.fechaCambio}"
    }

    override fun getItemCount(): Int = historial.size

    class HistorialViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val numeroCambio: TextView = view.findViewById<TextView>(R.id.TextViewCambio)
        val campoModificado: TextView = view.findViewById<TextView>(R.id.TextViewCampoModificado)
        val valorAnterior: TextView = view.findViewById<TextView>(R.id.TextViewValorAnterior)
        val valorNuevo: TextView = view.findViewById<TextView>(R.id.TextViewValorNuevo)
        val fechaCambio: TextView = view.findViewById<TextView>(R.id.TextViewFechaCambio)
    }
}