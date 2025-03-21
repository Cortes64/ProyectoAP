package com.altrik.proyectoap.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.utilities.Usuario
import com.altrik.proyectoap.R

class UsuarioAdapter(private val usuarios: List<Usuario>) :
    RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_estudiante_list, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]
        holder.email.text = usuario.email
        holder.nombre.text = "${usuario.name} ${usuario.apellidos}"
        holder.tipoUsuario.text = usuario.tipoUsuario
    }

    override fun getItemCount(): Int = usuarios.size

    class UsuarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.student_list_nombre)
        val email: TextView = view.findViewById(R.id.student_list_correo)
        val tipoUsuario: TextView = view.findViewById(R.id.student_list_tipo)
    }
}
