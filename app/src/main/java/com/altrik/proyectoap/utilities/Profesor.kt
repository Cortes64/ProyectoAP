package com.altrik.proyectoap.utilities

class Profesor(
    override var correo: String,
    override var nombre: String,
    var telefono: String,
    var departamentoAcademico: String,
    override var contrasena: String
): Usuario(
    correo = correo,
    nombre = nombre,
    contrasena = contrasena,
    tipoUsuario = TipoUsuario.PROFESOR,
) {
    override fun toString(): String {
        return "Profesor(" +
                "correo='$correo', " +
                "nombre='$nombre', " +
                "telefono='$telefono', " +
                "departamentoAcademico='$departamentoAcademico', "
    }
}
