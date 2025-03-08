package com.altrik.proyectoap.utilities

import com.altrik.proyectoap.utilities.request.LoginRequest
import com.altrik.proyectoap.utilities.response.LoginResponse
import com.altrik.proyectoap.utilities.request.SignInRequest
import com.altrik.proyectoap.utilities.response.SignInResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun login(@Body usuario: LoginRequest): Call<LoginResponse>

    @POST("signIn")
    fun signIn(@Body usuario: SignInRequest): Call<SignInResponse>
}