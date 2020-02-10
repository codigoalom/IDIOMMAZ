package com.alm.idiommaz220;

import android.database.Cursor;

public class Leccion {
    DatabaseHelper idiommazDb;
    String NombreLeccion;
    String TextoAprender;
    String LeccionFkEnCont;
    String NivelFkEnCont;
    String ContenidoEnCont;
    Cursor FilasEnCont;
    String VozaTextoDeUsuario;

    public Leccion(){
    }

    public Leccion(String nombreleccion){
        NombreLeccion = nombreleccion;
    }

    public String getVozaTextoDeUsuario(){
        return VozaTextoDeUsuario;
    }

    public void setVozaTextoDeUsuario(String vozaTextoDeUsuario){
        VozaTextoDeUsuario = vozaTextoDeUsuario;
    }

    //Todo: implementar getters y setters para los atributos pendientes
    public String getContenidoEnCont(){
        return ContenidoEnCont;
    }

    public Cursor getFilasEnCont(){
        return FilasEnCont;
    }

    public void setFilasEnCont(Cursor filasEnCont){
        FilasEnCont = filasEnCont;
    }

    public void setContenidoEnCont(String contenidoEnCont){
        ContenidoEnCont = contenidoEnCont;
    }

    public String getNivelFkEnCont(){
        return NivelFkEnCont;
    }

    public void setNivelFkEnCont(String nivelFkEnCont){
        NivelFkEnCont = nivelFkEnCont;
    }

    public String getLeccionFkEnCont(){
        return LeccionFkEnCont;
    }

    public void setLeccionFkEnCont(String leccionFkEnCont){
        LeccionFkEnCont = leccionFkEnCont;
    }

    public String getTextoAprender(){
        return TextoAprender;
    }

    public void setTextoAprender(String textoAprender){
        TextoAprender = textoAprender;
    }

    public String getNomLeccion() {
        return NombreLeccion;
    }

    public void setNomLeccion(String leccion) {
        NombreLeccion = leccion;
    }
}
