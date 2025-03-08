package com.altrik.proyectoap.utilities

data class Usuario(
    var correo: String,
    var contrasena: String,
    var tipoUsuario: TipoUsuario
) {
    override fun toString(): String {
        return "Usuario(correo='$correo', contrasena='$contrasena', tipoUsuario=$tipoUsuario)"
    }
}
