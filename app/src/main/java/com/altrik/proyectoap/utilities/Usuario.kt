package com.altrik.proyectoap.utilities

data class Usuario(
    var correo: String,
    var nombre: String,
    var telefono: String?,
    var departamentoAcademico: String?,
    var nivelAcademico: String?,
    var facultad: String?,
    var carrera: String?,
    var contacto: String?,
    var contrasena: String,
    var tipoUsuario: TipoUsuario
) {
    override fun toString(): String {
        return "Usuario(" +
                "correo='$correo', " +
                "nombre='$nombre', " +
                "telefono=$telefono, " +
                "departamentoAcademico=$departamentoAcademico, " +
                "nivelAcademico=$nivelAcademico, " +
                "facultad=$facultad, " +
                "carrera=$carrera, " +
                "contacto=$contacto, " +
                "contrasena='$contrasena', " +
                "tipoUsuario=$tipoUsuario)"
    }
}
