package com.altrik.proyectoap.utilities.response

data class GetBecaResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?
)
