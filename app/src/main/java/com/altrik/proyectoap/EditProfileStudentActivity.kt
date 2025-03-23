package com.altrik.proyectoap

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.altrik.proyectoap.utilities.RetrofitClient
import com.altrik.proyectoap.utilities.Usuario
import com.altrik.proyectoap.utilities.request.UpdateUserRequest
import com.altrik.proyectoap.utilities.response.UpdateUserResponse
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_student_layout)

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val nombreUsuario = sharedPreferences.getString("nombreUsuario", "")
        val apellidosUsuario = sharedPreferences.getString("apellidosUsuario", "")

        val editButtonNombre = findViewById<ImageButton>(R.id.editButtonNombre)
        val editTextNombre = findViewById<EditText>(R.id.inputEditNombre)

        editTextNombre.setText(nombreUsuario)

        editButtonNombre.setOnClickListener {
            editTextNombre.isEnabled = true
        }

        val editButtonApellidos = findViewById<ImageButton>(R.id.editButtonApellidos)
        val editTextApellidos = findViewById<EditText>(R.id.inputEditApellidos)

        editTextApellidos.setText(apellidosUsuario)

        editButtonApellidos.setOnClickListener {
            editTextApellidos.isEnabled = true
        }

        val botonAplicarCambios = findViewById<Button>(R.id.boton_aplicar_cambios)
        botonAplicarCambios.setOnClickListener {
            guardarCambios()
        }
    }

    private fun guardarCambios() {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val emailUsuario = sharedPreferences.getString("emailUsuario", null)

        if (emailUsuario.isNullOrEmpty()) {
            Toast.makeText(this, "Error: No se encontró el email del usuario", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val nombre = findViewById<EditText>(R.id.inputEditNombre).text.toString()
        val apellidos = findViewById<EditText>(R.id.inputEditApellidos).text.toString()

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.updateUser(emailUsuario, UpdateUserRequest(nombre, apellidos))
                if (response.success && response.data != null) {
                    val usuarioActualizado = response.data
                    sharedPreferences.edit()
                        .putString("nombreUsuario", usuarioActualizado.name)
                        .putString("apellidosUsuario", usuarioActualizado.apellidos)
                        .apply()
                }
            } catch(e: Exception) {
                Toast.makeText(this@EditProfileStudentActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}