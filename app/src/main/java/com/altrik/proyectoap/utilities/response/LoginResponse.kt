package com.altrik.proyectoap.utilities.response

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val tipoUsuario: String?
)