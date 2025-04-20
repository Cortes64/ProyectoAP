package com.altrik.proyectoap.utilities.response

import com.altrik.proyectoap.utilities.EstudiantesInteresados

data class EstudianteInteresadoResponse (
    val message: String,
    val success: Boolean,
    val estudianteInteresado: EstudiantesInteresados
)