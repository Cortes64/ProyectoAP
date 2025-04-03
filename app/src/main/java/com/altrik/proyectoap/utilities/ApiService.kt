package com.altrik.proyectoap.utilities

import com.altrik.proyectoap.utilities.request.LoginRequest
import com.altrik.proyectoap.utilities.response.LoginResponse
import com.altrik.proyectoap.utilities.request.SignInRequest
import com.altrik.proyectoap.utilities.request.UpdateUserRequest
import com.altrik.proyectoap.utilities.response.SignInResponse
import com.altrik.proyectoap.utilities.response.UpdateUserResponse
import com.altrik.proyectoap.utilities.response.UserListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    fun login(@Body usuario: LoginRequest): Call<LoginResponse>

    @POST("signIn")
    fun signIn(@Body usuario: SignInRequest): Call<SignInResponse>

    @GET("userList/{name}")
    suspend fun getListaUsuarios(@Path("name") name: String): UserListResponse<List<Usuario>>

    @PUT("user/{email}")
    suspend fun updateUser(@Path("email") email: String, @Body usuario: UpdateUserRequest): UpdateUserResponse<Usuario>

    @GET("escuelas")
    suspend fun getEscuelas(): UserListResponse<List<Usuario>>
}