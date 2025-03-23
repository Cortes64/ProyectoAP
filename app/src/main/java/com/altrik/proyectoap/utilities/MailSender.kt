package com.altrik.proyectoap.utilities

import android.content.Context
import java.util.Properties
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object MailSender {

    fun sendEmail(context: Context, to: String, subject: String, body: String) {
        Thread {
            try {
                // Leer credenciales desde mail_config.properties en assets
                val properties = context.assets.open("mail_config.properties").bufferedReader().use { it.readText() }

                val propsMap = properties.lines().associate {
                    val (key, value) = it.split("=")
                    key.trim() to value.trim()
                }

                val username = propsMap["EMAIL_USER"]
                    ?: throw Exception("No se encontró EMAIL_USER en mail_config.properties")
                val password = propsMap["EMAIL_PASSWORD"]
                    ?: throw Exception("No se encontró EMAIL_PASSWORD en mail_config.properties")

                // Configuración del servidor SMTP (Gmail)
                val props = Properties().apply {
                    put("mail.smtp.auth", "true")
                    put("mail.smtp.starttls.enable", "true")  // Habilitar STARTTLS
                    put("mail.smtp.host", "smtp.gmail.com")  // Servidor SMTP Gmail
                    put("mail.smtp.port", "587")  // Puerto STARTTLS
                    put("mail.smtp.ssl.protocols", "TLSv1.2")  // TLS 1.2
                    put("mail.smtp.connectiontimeout", "10000")  // 10 segundos timeout
                    put("mail.smtp.timeout", "10000")
                    put("mail.smtp.writetimeout", "10000")
                    put("mail.smtp.ssl.trust", "smtp.gmail.com")
                }

                val session = Session.getInstance(props, object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(username, password)
                    }
                }).apply {
                    debug = true  // Habilitar logs para debugging
                }

                // Construir el mensaje
                val message = MimeMessage(session).apply {
                    setFrom(InternetAddress(username))  // Remitente
                    setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
                    setSubject(subject)
                    setText(body)  // Texto del correo
                }

                // Enviar correo
                Transport.send(message)
                println("Correo enviado correctamente a $to")

            } catch (e: MessagingException) {
                e.printStackTrace()
                println("Error al enviar el correo: ${e.message}")
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error general: ${e.message}")
            }
        }.start()  // Ejecutar en un hilo separado
    }
}
