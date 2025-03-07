package com.altrik.proyectoap.utilities;

public class Usuario {
    private String correo;
    private String contrasena;
    private TipoUsuario tipoUsuario;

    public Usuario(String correo, String contrasena, TipoUsuario tipoUsuario) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
    }

    public String getCorreo() {
        return correo
    }

    public String getContrasena() {
        return contrasena;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                ", correo='" + correo + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                '}';
    }
}
