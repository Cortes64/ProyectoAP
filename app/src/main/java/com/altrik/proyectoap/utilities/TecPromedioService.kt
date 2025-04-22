package com.altrik.proyectoap.utilities

import retrofit2.Response
import retrofit2.http.*

interface TecPromedioService {
    @POST("tda-expediente-estudiantil/ajax/generar_tabla_promedio_ponderado")
    suspend fun obtenerPromedio(): Response<String>
}