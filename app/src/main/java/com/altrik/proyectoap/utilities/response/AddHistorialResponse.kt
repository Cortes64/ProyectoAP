package com.altrik.proyectoap.utilities.response

import com.altrik.proyectoap.utilities.Historial

data class AddHistorialResponse(
    val success: Boolean,
    val message: String,
    val historial: Historial?
)
