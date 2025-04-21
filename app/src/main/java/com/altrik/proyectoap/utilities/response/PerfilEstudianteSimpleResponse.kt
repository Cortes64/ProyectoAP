package com.altrik.proyectoap.utilities.response

import com.altrik.proyectoap.utilities.PerfilEstudianteSimple

data class PerfilEstudianteSimpleResponse(
    val success: Boolean,
    val message: String?,
    val data: PerfilEstudianteSimple?
)
