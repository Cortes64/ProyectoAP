package com.altrik.proyectoap.utilities

import android.app.Activity
import android.content.Context
import com.altrik.proyectoap.R
import com.google.android.material.navigation.NavigationView
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.altrik.proyectoap.FormBuscarEstudianteActivity
import com.altrik.proyectoap.FormBuscarOfertaActivity
import com.altrik.proyectoap.LoginActivity
import com.altrik.proyectoap.MenuAdminActivity
import com.altrik.proyectoap.MenuProfessorActivity
import com.altrik.proyectoap.MenuSchoolActivity
import com.altrik.proyectoap.MenuStudentActivity
import com.altrik.proyectoap.SeguimientoActivity

object NavViewHelper {
    fun configurarMenu(navView: NavigationView, tipoUsuario: String) {
        navView.menu.clear()

        when (tipoUsuario) {
            "ESTUDIANTE" -> navView.inflateMenu(R.menu.sidebar_student_menu)
            "PROFESOR", "ADMINISTRADOR", "ESCUELA" -> navView.inflateMenu(R.menu.sidebar_admin_menu)
        }
    }

    fun configurarListeners(context: Context, navView: NavigationView, tipoUsuario: String) {

        val nombreUsuario = context.getSharedPreferences("UserPrefs", MODE_PRIVATE)
            .getString("nombreUsuario", "")

        val sidebarNombre = navView
            .getHeaderView(0)
            .findViewById<TextView>(R.id.sidebarNombre)

        sidebarNombre.text = nombreUsuario

        when (tipoUsuario) {
            "ESTUDIANTE" -> navView.setNavigationItemSelectedListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.nav_inicio -> {irMenu(context, tipoUsuario); true}
                    R.id.nav_buscar_oferta -> {irBuscarOferta(context); true}
                    R.id.nav_seguimiento -> {irSeguimiento(context); true}
                    R.id.nav_cerrar_sesion -> {cerrarSesion(context); true}
                    else -> false
                }
            }
            "PROFESOR", "ADMINISTRADOR", "ESCUELA" -> navView.setNavigationItemSelectedListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.nav_inicio -> {irMenu(context, tipoUsuario); true}
                    R.id.nav_gestionar_usuarios -> {irGestionarUsuarios(context); true}
                    R.id.nav_buscar_publicaciones -> {true} // ...?
                    R.id.nav_cerrar_sesion -> {cerrarSesion(context); true}
                    else -> false
                }
            }
        }
    }

    private fun irMenu(context: Context, tipoUsuario: String) {
        when (tipoUsuario) {
            "ESTUDIANTE" -> {
                val intent = Intent(context, MenuStudentActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finish()
            }
            "PROFESOR" -> {
                val intent = Intent(context, MenuProfessorActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finish()
            }
            "ADMINISTRADOR" -> {
                val intent = Intent(context, MenuAdminActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finish()
            }
            "ESCUELA" -> {
                val intent = Intent(context, MenuSchoolActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finish()
            }
        }
    }
    private fun irBuscarOferta(context: Context) {
        val intent = Intent(context, FormBuscarOfertaActivity::class.java)
        context.startActivity(intent)
        (context as Activity).finish()
    }

    private fun irSeguimiento(context: Context) {
        val intent = Intent(context, SeguimientoActivity::class.java)
        context.startActivity(intent)
        (context as Activity).finish()
    }

    private fun cerrarSesion(context: Context) {
        val sharedPref = context.getSharedPreferences("UserPrefs", MODE_PRIVATE).edit()
        sharedPref.clear()
        sharedPref.apply()

        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
        (context as Activity).finish()
    }

    private fun irGestionarUsuarios(context: Context) {
        val intent = Intent(context, FormBuscarEstudianteActivity::class.java)
        context.startActivity(intent)
        (context as Activity).finish()
    }
}