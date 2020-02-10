package com.alm.idiommaz220;

public class Usuario {
    String NomUsuario;
    String ContVisto;
    String Comenzar;

    public Usuario(){

    }

    public Usuario(String nomusuario, String contvisto){
        NomUsuario = nomusuario;
        ContVisto = contvisto;
    }

    public String getNomUsuario() {
        return NomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        NomUsuario = nomUsuario;
    }

    public String getContVisto() {
        return ContVisto;
    }

    public void setContVisto(String contVisto) {
        ContVisto = contVisto;
    }

    public String getComenzar() {
        return Comenzar;
    }

    public void setComenzar(String comenzar) {
        Comenzar = comenzar;
    }
}
