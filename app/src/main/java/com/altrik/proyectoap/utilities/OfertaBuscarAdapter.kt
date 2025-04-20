package com.altrik.proyectoap.utilities


import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.R
import com.altrik.proyectoap.RevisarInteresadosActivity
import com.google.gson.Gson

class OfertaBuscarAdapter (
    private val ofertas: List<Oferta>,
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

        holder.chatButton.setOnClickListener {
            // Lógica para abrir la oferta
        }

        holder.personButton.setOnClickListener {
            val context = holder.itemView.context
            val gson = Gson()
            val intent = Intent(context, RevisarInteresadosActivity::class.java).apply {
                putExtra("oferta", gson.toJson(oferta))
            }
            context.startActivity(intent)
        }

        holder.addButton.setOnClickListener {
            val nuevoEstudiante = EstudiantesInteresados(
                nombre = "Nuevo Estudiante",
                correoEstudiante = "correo@ejemplo.com",
                fechaRegistro = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                aceptado = false
            )

            oferta.estudiantesInteresados.add(nuevoEstudiante)
            notifyItemChanged(position)
            Toast.makeText(holder.itemView.context, "Estudiante añadido!", Toast.LENGTH_SHORT)
                .show()
        }




        holder.personButton.setOnClickListener {
            val context = holder.itemView.context

            val gson = Gson()
            val estudiantesJson = gson.toJson(oferta.estudiantesInteresados)
            val historialJson = gson.toJson(oferta.historial)

            val intent = Intent(context, RevisarInteresadosActivity::class.java)
            intent.putExtra("titulo", oferta.titulo)
            intent.putExtra("tipoTrabajo", oferta.tipoTrabajo)
            intent.putExtra("departamento", oferta.departamento)
            intent.putExtra("descripcion", oferta.descripcion)
            intent.putExtra("profesor", oferta.profesor)
            intent.putExtra("fechaInicio", oferta.fechaInicio)
            intent.putExtra("fechaFin", oferta.fechaFin)
            intent.putExtra("objetivos", oferta.objetivos)
            intent.putExtra("cantidadVacantes", oferta.cantidadVacantes)
            intent.putExtra("duracion", oferta.duracion)
            intent.putExtra("requisitos", oferta.requisitos)
            intent.putExtra("estadoOferta", oferta.estadoOferta)
            intent.putExtra("estudiantesInteresados", estudiantesJson)
            intent.putExtra("historialJson", historialJson)
            context.startActivity(intent)
        }



    }


    override fun getItemCount(): Int = ofertas.size

    class OfertaViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.textOfertaTitle)
        val tipoTrabajo: TextView = view.findViewById(R.id.textTipoOferta)
        val descripcion: TextView = view.findViewById(R.id.textDescripcionOferta)
        val chatButton: ImageButton = view.findViewById(R.id.chat_button)
        val personButton: ImageButton = view.findViewById(R.id.person_button)
        val addButton: ImageButton = view.findViewById(R.id.add_button)
    }
}