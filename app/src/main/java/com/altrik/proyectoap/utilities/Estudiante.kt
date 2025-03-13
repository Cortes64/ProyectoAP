package com.altrik.proyectoap.utilities

class Estudiante(
    override var correo: String,
    override var nombre: String,
    var telefono: String,
    var nivelAcademico: String,
    var contacto: String,
    override var contrasena: String,
    var carrera: String
): Usuario(
    correo = correo,
    nombre = nombre,
    contrasena = contrasena,
    tipoUsuario = TipoUsuario.ESTUDIANTE
) {
    override fun toString(): String {
        return "Estudiante(" +
                "correo='$correo', " +
                "nombre='$nombre', " +
                "telefono='$telefono', " +
                "nivelAcademico='$nivelAcademico', " +
                "contacto='$contacto', " +
                "contrasena='$contrasena', " +
                "tipoUsuario=$tipoUsuario)" +
                "carrera='$carrera')"

    }
}