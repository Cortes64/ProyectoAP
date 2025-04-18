package com.altrik.proyectoap.utilities.response

data class EvaluacionResponse(
    val success: Boolean,
    val message: String,
    val error: String? = null
)