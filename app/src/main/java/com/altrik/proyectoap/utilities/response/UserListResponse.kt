package com.altrik.proyectoap.utilities.response

data class UserListResponse<T> (
    val success: Boolean,
    val message: String?,
    val data: T?
)