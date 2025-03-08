package com.altrik.proyectoap.utilities.request

data class SignInRequest (
    val correo: String,
    val contrasena: String,
    val tipo_usuario: String
)