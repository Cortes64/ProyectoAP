package com.altrik.proyectoap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout

class StatusFinancieroActivity: AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.status_financiero_layout)

        drawerLayout = findViewById(R.id.drawer_layout)
    }


}