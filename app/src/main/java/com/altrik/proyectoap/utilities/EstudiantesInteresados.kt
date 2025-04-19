package com.altrik.proyectoap.utilities

data class  EstudiantesInteresados (
    val nombre: String,
    val correoEstudiante: String,  // Nuevo parámetro requerido
    val fechaRegistro: String,
    val aceptado: Boolean          // Otro parámetro requerido
)