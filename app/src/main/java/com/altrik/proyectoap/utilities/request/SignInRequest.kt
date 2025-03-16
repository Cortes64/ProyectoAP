package com.altrik.proyectoap.utilities.request

data class SignInRequest (
    val email: String,
    val name: String,
    val apellidos: String,
    val escuela: String?,
    val zonaTrabajo: String?,
    val password: String,
    val tipoUsuario: String
)