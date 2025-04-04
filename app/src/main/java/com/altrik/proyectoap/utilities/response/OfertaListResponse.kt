package com.altrik.proyectoap.utilities.response

class OfertaListResponse<T> (
    val success: Boolean,
    val message: String?,
    val data: T?
)