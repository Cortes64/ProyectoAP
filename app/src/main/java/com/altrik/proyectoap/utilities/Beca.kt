package com.altrik.proyectoap.utilities

data class Beca (
    val nombre: String,
    val description: String,
    val requisitos: MutableList<String> = mutableListOf(),
    val beneficios: MutableList<String> = mutableListOf(),
    val procesoObtencion: String,
    val estudiante: String,
    val tipo: String,
    val grupo: String?
)