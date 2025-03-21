package com.altrik.proyectoap.utilities.response

import com.altrik.proyectoap.utilities.Usuario

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val usuario: Usuario?
)