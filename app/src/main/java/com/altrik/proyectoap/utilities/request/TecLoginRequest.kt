package com.altrik.proyectoap.utilities.request

import com.google.gson.annotations.SerializedName

data class TecLoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("return_url")
    val returnUrl: String = "/dotlrn/index"
)
