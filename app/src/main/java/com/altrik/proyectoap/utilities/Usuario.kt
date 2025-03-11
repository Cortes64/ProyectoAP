package com.altrik.proyectoap.utilities

open class Usuario(
    open var correo: String,
    open var nombre: String,
    open var contrasena: String,
    var tipoUsuario: TipoUsuario
) {
    override fun toString(): String {
        return "Usuario(" +
                "correo='$correo', " +
                "nombre='$nombre', " +
                "contrasena='$contrasena', " +
                "tipoUsuario=$tipoUsuario)"
    }
}
