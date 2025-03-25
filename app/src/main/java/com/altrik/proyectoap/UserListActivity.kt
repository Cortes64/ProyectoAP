package com.altrik.proyectoap

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altrik.proyectoap.utilities.RetrofitClient
import com.altrik.proyectoap.utilities.Usuario
import com.altrik.proyectoap.utilities.UsuarioAdapter
import kotlinx.coroutines.launch
import android.content.Intent

class UserListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UsuarioAdapter
    private val listaUsuarios = mutableListOf<Usuario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_list_layout)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = UsuarioAdapter(listaUsuarios) { usuario ->
            // Manejar click del usuario

            val intent = Intent(this, ManageStudentActivity::class.java).apply {
                putExtra("NOMBRE", usuario.name)
                putExtra("APELLIDOS", usuario.apellidos)
                putExtra("EMAIL", usuario.email)
                putExtra("TIPO", usuario.tipoUsuario)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        val sharedPreferences = getSharedPreferences("SearchUser", MODE_PRIVATE)
        val nombreBuscado = sharedPreferences.getString("nombreBuscado", "")
        fetchUsuarios(nombreBuscado.toString())
    }

    private fun fetchUsuarios(name: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getListaUsuarios(name)
                if (response.success && response.data != null) {
                    listaUsuarios.clear()
                    listaUsuarios.addAll(response.data)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@UserListActivity, "No se encontraron usuarios", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@UserListActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}