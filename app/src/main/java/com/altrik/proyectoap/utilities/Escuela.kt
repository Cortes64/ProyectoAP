package com.altrik.proyectoap.utilities

class Escuela(
    override var nombre: String,
    override var correo: String,
    var facultad: String,
    var telefono: String,
    var carrera: String,
    override var contrasena: String,
): Usuario(
    nombre = nombre,
    correo = correo,
    contrasena = contrasena,
    tipoUsuario = TipoUsuario.ESCUELA
) {
}