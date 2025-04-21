package com.altrik.proyectoap.utilities.response

import com.altrik.proyectoap.utilities.Oferta

data class OfertaByTitleResponse(
    val success: Boolean,
    val message: String?,
    val data: Oferta?
)


