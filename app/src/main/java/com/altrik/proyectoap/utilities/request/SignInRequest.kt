package com.altrik.proyectoap.utilities.request

data class SignInRequest (
    val email: String,
    val name: String,
    val apellidos: String,
    val escuela: String?,
    val zonaTrabajo: String?,
    val carnet: String?,
    val password: String,
    val tipoUsuario: String,
    val departamentoTrabajo: String?,
    val telefono: String?,
    val nivelAcademico: String?,
    val contacto: String?,
    val carrera: String?,
    val promedioPonderado: String?
)