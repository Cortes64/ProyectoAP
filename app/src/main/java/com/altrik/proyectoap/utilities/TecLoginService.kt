package com.altrik.proyectoap.utilities

import com.altrik.proyectoap.utilities.request.TecLoginRequest
import retrofit2.Response
import retrofit2.http.*

interface TecLoginService {
    @Headers("Content-Type: application/json")
    @POST("api/login/new-form")
    suspend fun login(@Body credentials: TecLoginRequest): Response<String>
}