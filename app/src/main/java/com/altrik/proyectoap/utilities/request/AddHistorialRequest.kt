package com.altrik.proyectoap.utilities.request

data class AddHistorialRequest(
    val campoModificado: String,
    val valorAnterior: String,
    val valorNuevo: String
)