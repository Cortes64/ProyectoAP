package com.altrik.proyectoap.utilities.response

data class UpdateUserResponse<T> (
    val success: Boolean,
    val message: String,
    val data: T?
)