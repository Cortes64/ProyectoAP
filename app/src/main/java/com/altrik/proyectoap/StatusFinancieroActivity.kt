package com.altrik.proyectoap

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.altrik.proyectoap.utilities.Beca
import com.altrik.proyectoap.utilities.FooterBarView
import com.altrik.proyectoap.utilities.NavViewHelper
import com.altrik.proyectoap.utilities.RetrofitClient
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class StatusFinancieroActivity: AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.status_financiero_layout)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        val headerView = navView.getHeaderView(0)
        val sidebarNombre = headerView.findViewById<TextView>(R.id.sidebarNombre)

        val nombreUsuario = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            .getString("nombreUsuario", "")

        sidebarNombre.text = nombreUsuario

        val imageButtonSidebar = findViewById<ImageButton>(R.id.imageButtonSidebar)
        imageButtonSidebar.setOnClickListener {
            abrirSidebar()
        }

        val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val tipoUsuario = sharedPrefs.getString("tipoUsuario", "") ?: "ESTUDIANTE"
        val footer = findViewById<FooterBarView>(R.id.footerBar)

        footer.configurarPara(tipoUsuario)
        NavViewHelper.configurarMenu(navView, tipoUsuario);
        NavViewHelper.configurarListeners(this, navView, tipoUsuario)

        fetchBeca()
    }

    @SuppressLint("SetTextI18n")
    private fun fetchBeca() {
        val email = getSharedPreferences("UserPrefs", MODE_PRIVATE).getString("email", "")

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getBecaByEmail(email.toString())
                if (response.success) {
                    val beca = response.data
                    val textViewTipoBeca = findViewById<TextView>(R.id.textViewTipoBeca)
                    val textViewBeneficioBeca = findViewById<TextView>(R.id.textViewBeneficiosBeca)

                    textViewTipoBeca.text = "Tipo de beca: ${beca?.tipo}"
                    textViewBeneficioBeca.text = "Beneficios: ${beca?.descripcion}"
                } else {
                    Toast.makeText(this@StatusFinancieroActivity, "No se encontró la beca", Toast.LENGTH_SHORT).show()
                }
            }
            catch (e: Exception) {
                Toast.makeText(this@StatusFinancieroActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                println("${e.message}")
            }
        }
    }

    private fun abrirSidebar() {
        drawerLayout.openDrawer(GravityCompat.START)
    }
}