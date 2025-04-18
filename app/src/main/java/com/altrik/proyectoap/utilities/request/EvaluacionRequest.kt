package com.altrik.proyectoap.utilities.request

data class EvaluacionRequest(
    val rating: Int,
    val comments: String,
    val studentEmail: String  // Changed from studentId to email
)