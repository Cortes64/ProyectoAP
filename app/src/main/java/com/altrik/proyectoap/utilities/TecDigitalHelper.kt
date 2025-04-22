package com.altrik.proyectoap.utilities

import android.util.Log
import com.altrik.proyectoap.utilities.request.TecLoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import com.google.gson.Gson

object TecDigitalHelper {
    suspend fun obtenerPromedioTec(email: String, password: String): String? =
        withContext(Dispatchers.IO) {
            try {
                val request = TecLoginRequest(email, password, "/dotlrn/index")
                Log.d("TEC", "Login JSON: ${Gson().toJson(request)}")
                Log.d("TEC", "Iniciando login con: $email")

                val loginResponse = TecRetrofitClient.loginService.login(request)
                Log.d("TEC", "Código de respuesta login: ${loginResponse.code()}")

                if (!loginResponse.isSuccessful) {
                    Log.e("TEC", "Fallo login. Código: ${loginResponse.code()}, body: ${loginResponse.errorBody()?.string()}")
                    return@withContext null
                }

                // ✅ Ahora las cookies ya están guardadas automáticamente.
                val promedioResponse: Response<String> = TecRetrofitClient.promedioService.obtenerPromedio()
                Log.d("TEC", "Código de respuesta promedio: ${promedioResponse.code()}")

                if (promedioResponse.isSuccessful) {
                    val html = promedioResponse.body() ?: return@withContext null
                    Log.d("TEC", "HTML recibido: $html")
                    return@withContext PromedioExtractor.extraerPromedio(html)
                } else {
                    Log.e("TEC", "Fallo obtener promedio. Código: ${promedioResponse.code()}, error: ${promedioResponse.errorBody()?.string()}")
                }

                return@withContext null
            } catch (e: Exception) {
                Log.e("TEC", "Error inesperado: ${e.message}", e)
                return@withContext null
            }
        }
}