package com.altrik.proyectoap.utilities.request

data class SignInRequest (
    val correo: String,
    val nombre: String,
    val telefono: String?,
    val departamentoAcademico: String?,
    val nivelAcademico: String?,
    val facultad: String?,
    val carrera: String?,
    val contacto: String?,
    val contrasena: String,
    val tipoUsuario: String
)