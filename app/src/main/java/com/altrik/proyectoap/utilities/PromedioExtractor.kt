package com.altrik.proyectoap.utilities

import org.jsoup.Jsoup

object PromedioExtractor {
    fun extraerPromedio(html: String): String? {
        val doc = Jsoup.parse(html)
        val filas = doc.select("#tPromedioPonderado tbody tr")

        for (fila in filas) {
            val celdas = fila.select("td")
            if (celdas.size >= 4) {
                val promedioText = celdas[3].text().trim()
                if (promedioText != "0" && promedioText.isNotEmpty()) {
                    return promedioText
                }
            }
        }
        return null
    }
}