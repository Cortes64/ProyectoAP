package com.altrik.proyectoap.utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.startActivity
import com.altrik.proyectoap.CrearOfertaActivity
import com.altrik.proyectoap.EditProfileStudentActivity
import com.altrik.proyectoap.MenuAdminActivity
import com.altrik.proyectoap.MenuProfessorActivity
import com.altrik.proyectoap.MenuSchoolActivity
import com.altrik.proyectoap.MenuStudentActivity
import com.altrik.proyectoap.R
import com.altrik.proyectoap.SeguimientoActivity
import java.util.Locale

class FooterBarView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null
) : LinearLayout(context, attr)
{
    private lateinit var tipoUsuario: String;

    init {
        orientation = HORIZONTAL
    }

    fun configurarPara(tipoUsuario: String) {
        this.tipoUsuario = tipoUsuario.uppercase(Locale.ROOT)
        removeAllViews()

        val layoutId = when (this.tipoUsuario) {
            "ESTUDIANTE" -> R.layout.footer_estudiante
            "ADMINISTRADOR", "ESCUELA", "PROFESOR" -> R.layout.footer_admin
            else -> null
        }

        layoutId?.let {
            LayoutInflater.from(context).inflate(it, this, true)
            setupListeners()
        }
    }

    private fun setupListeners() {
        findViewById<ImageButton?>(R.id.imageFolderPlus)?.setOnClickListener {
            val intent = Intent(context, CrearOfertaActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()
        }

        findViewById<ImageButton?>(R.id.imageFolderCheck)?.setOnClickListener {
            var intent: Intent? = null
            when (tipoUsuario) {
                "ADMINISTRADOR" -> { intent = Intent(context, MenuAdminActivity::class.java) }
                "ESCUELA" -> { intent = Intent(context, MenuSchoolActivity::class.java) }
                "PROFESOR" -> { intent = Intent(context, MenuProfessorActivity::class.java) }
            }
            context.startActivity(intent)
            (context as Activity).finish()
        }

        findViewById<ImageButton?>(R.id.imageButtonEdit)?.setOnClickListener {
            var intent: Intent? = null
            // Deberia cambiarse cada uno por su respectivo.
            when (tipoUsuario) {
                "ADMINISTRADOR" -> {intent = Intent(context, EditProfileStudentActivity::class.java)}
                "ESCUELA" -> {intent = Intent(context, EditProfileStudentActivity::class.java)}
                "PROFESOR" -> {intent = Intent(context, EditProfileStudentActivity::class.java)}
                "ESTUDIANTE" -> {intent = Intent(context, EditProfileStudentActivity::class.java)}
            }
            context.startActivity(intent)
            (context as Activity).finish()
        }

        findViewById<ImageButton?>(R.id.imageButtonMenu)?.setOnClickListener {
            val intent = Intent(context, SeguimientoActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()
        }

        findViewById<ImageButton?>(R.id.imageButtonDollar)?.setOnClickListener {
            val intent = Intent(context, MenuStudentActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }
}