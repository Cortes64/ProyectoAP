package com.altrik.proyectoap.utilities

import com.altrik.proyectoap.utilities.request.AddHistorialRequest
import com.altrik.proyectoap.utilities.request.LoginRequest
import com.altrik.proyectoap.utilities.response.LoginResponse
import com.altrik.proyectoap.utilities.request.SignInRequest
import com.altrik.proyectoap.utilities.request.UpdateOfertaRequest
import com.altrik.proyectoap.utilities.request.UpdateUserRequest
import com.altrik.proyectoap.utilities.response.AddHistorialResponse
import com.altrik.proyectoap.utilities.response.EstudianteInteresadoResponse
import com.altrik.proyectoap.utilities.response.GetBecaResponse
import com.altrik.proyectoap.utilities.response.OfertaByTitleResponse
import com.altrik.proyectoap.utilities.response.OfertaListResponse
import com.altrik.proyectoap.utilities.response.OfertaResponse
import com.altrik.proyectoap.utilities.response.PerfilEstudianteSimpleResponse
import com.altrik.proyectoap.utilities.response.SignInResponse
import com.altrik.proyectoap.utilities.response.UpdateUserResponse
import com.altrik.proyectoap.utilities.response.UserListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // Usuarios

    @POST("login")
    fun login(@Body usuario: LoginRequest): Call<LoginResponse>

    @POST("signIn")
    fun signIn(@Body usuario: SignInRequest): Call<SignInResponse>

    @GET("userList/{name}")
    suspend fun getListaUsuarios(@Path("name") name: String): UserListResponse<List<Usuario>>

    @PUT("users/{email}")
    suspend fun updateUser(@Path("email") email: String, @Body usuario: UpdateUserRequest): UpdateUserResponse<Usuario>

    @GET("usersByType/{tipoUsuario}")
    suspend fun getUsuariosPorTipo(@Path("tipoUsuario") tipoUsuario: String): UserListResponse<List<Usuario>>

    @GET("perfilEstudianteSimple")
    suspend fun getPerfilEstudianteSimple(
        @Path("email") email: String
    ): PerfilEstudianteSimpleResponse

    // Ofertas

    @GET("ofertas")
    suspend fun getOfertas(): List<Oferta>

    @GET("oferta/{titulo}")
    suspend fun getOfertaByTitle(@Path("titulo") titulo: String): OfertaByTitleResponse

    @POST("oferta")
    fun createOferta(@Body oferta: Oferta): Call<OfertaResponse>

    @PUT("oferta/{titulo}")
    suspend fun updateOferta(@Path("titulo") titulo: String, @Body oferta: UpdateOfertaRequest): OfertaResponse

    @DELETE("/oferta/{titulo}")
    suspend fun deleteOferta(
        @Path("titulo") titulo: String
    )

    @GET("filtroOfertas")
    suspend fun filtrarOfertas(
        @Query("titulo") titulo: String?,
        @Query("requisitos") requisitos: String?,
        @Query("departamento") departamento: String?
    ): OfertaListResponse<List<Oferta>>

    @POST("oferta/{titulo}/estudiante")
    suspend fun addEstudianteInteresado(
        @Path("titulo") titulo: String,
        @Body estudianteInteresado: EstudiantesInteresados
    ): EstudianteInteresadoResponse

    @DELETE("oferta/{titulo}/estudiante/{correo}")
    suspend fun removeEstudianteInteresado(
        @Path("titulo") titulo: String,
        @Path("correo") correo: String
    ): Response<Void>

    @PUT("oferta/{titulo}/estudiante/{correo}")
    suspend fun updateEstudianteInteresado(
        @Path("titulo") titulo: String,
        @Path("correo") correo: String,
    ): Response<Void>


    @POST("/oferta/{titulo}/historial")
    suspend fun addToHistorial(
        @Path("titulo") titulo: String,
        @Body historial: AddHistorialRequest
    ): AddHistorialResponse

    // Becas

    @GET("becaByEmail/{email}")
    suspend fun getBecaByEmail(@Path("email") email: String): GetBecaResponse<Beca>

    // Reportes

    @GET("getReportes")
    suspend fun getReportes(): List<Reporte>
}
