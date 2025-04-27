package com.altrik.proyectoap.utilities.response

import com.altrik.proyectoap.utilities.Beca

data class BecaResponse(
    val success: Boolean,
    val message: String,
    val data: Beca?
)
