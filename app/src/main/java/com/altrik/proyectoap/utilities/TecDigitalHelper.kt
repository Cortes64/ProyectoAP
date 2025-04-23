package com.altrik.proyectoap.utilities

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header

class TecDigitalHelper {
    private val urlBase = "https://tecdigital.tec.ac.cr/"
    private var adSessionId: String? = null
    private var adUserLogin: String? = null

    data class LoginRequest(
        val email: String,
        val password: String,
        val returnUrl: String = "/dotlrn/index"
    )

    private interface TECDigitalService {
        @POST("api/login/new-form/")
        suspend fun login(@Body loginRequest: LoginRequest): Response<ResponseBody>

        @POST("tda-expediente-estudiantil/ajax/generar_tabla_promedio_ponderado")
        suspend fun obtenerPromedio(@Header("Cookie") sessionCookie: String): Response<ResponseBody>
    }

    private val okHttpClient = OkHttpClient.Builder()
        // Esto es porque el okHttp censuraba las cookies del response
        .addInterceptor { chain ->
            val response = chain.proceed(chain.request())

            val cookies = response.headers("Set-Cookie")
            cookies.forEach { cookie ->
                when {
                    cookie.contains("ad_session_id") -> {
                        adSessionId = Regex("""ad_session_id="([^;]+)"""").find(cookie)?.groupValues?.get(1)
                    }
                    cookie.contains("ad_user_login") -> {
                        adUserLogin = Regex("""ad_user_login="([^"]+)"""").find(cookie)?.groupValues?.get(1)
                    }
                }
            }

            response
        }
        .followRedirects(true)
        .followSslRedirects(true)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(urlBase)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(TECDigitalService::class.java)

    suspend fun obtenerPromedioTec(email: String, password: String): String {
        return try {
            val loginResponse = service.login(LoginRequest(email, password))

            if (!loginResponse.isSuccessful) {
                throw Exception("Error en el login: ${loginResponse.code()}")
            }

            if (adSessionId == null || adUserLogin == null) {
                return "ERR_401"
                // Contraseña o mail incorrecto.
            }

            val cookieString = """ad_session_id="$adSessionId"; ad_user_login="$adUserLogin""""
            val promedioResponse = service.obtenerPromedio(cookieString)

            if (!promedioResponse.isSuccessful) {
                throw Exception("Error al obtener promedio: ${promedioResponse.code()}")
            }

            val htmlTabla = promedioResponse.body()?.string() ?: throw Exception("ERR_NO_RESPONSE")
            val tabla = Result.success(htmlTabla).getOrDefault("ERR_NO_RESPONSE")

            PromedioExtractor.extraerPromedio(tabla) ?: "ERR_PARSE"

        } catch (e: Exception) {
            e.printStackTrace()
            "ERR_DESCONOCIDO"
        }
    }
}