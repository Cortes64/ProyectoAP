package com.altrik.proyectoap.utilities

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.getValue

object TecRetrofitClient {
    private val cookieJar = object : okhttp3.CookieJar {
        private val cookieStore = mutableMapOf<String, List<okhttp3.Cookie>>()

        override fun saveFromResponse(url: okhttp3.HttpUrl, cookies: List<okhttp3.Cookie>) {
            cookieStore[url.host] = cookies
        }

        override fun loadForRequest(url: okhttp3.HttpUrl): List<okhttp3.Cookie> {
            return cookieStore[url.host] ?: listOf()
        }
    }

    private val client = okhttp3.OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .build()


    val loginService: TecLoginService by lazy {
        Retrofit.Builder()
            .baseUrl("https://tecdigital.tec.ac.cr/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(TecLoginService::class.java)
    }

    val promedioService: TecPromedioService by lazy {
        Retrofit.Builder()
            .baseUrl("https://tecdigital.tec.ac.cr/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(TecPromedioService::class.java)
    }
}
