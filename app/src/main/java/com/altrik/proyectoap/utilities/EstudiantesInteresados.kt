package com.altrik.proyectoap.utilities

data class  EstudiantesInteresados (
    val nombre: String,
    val correoEstudiante: String,
    val fechaRegistro: String,
    var aceptado: Boolean,
    val promedioPonderado: String
)