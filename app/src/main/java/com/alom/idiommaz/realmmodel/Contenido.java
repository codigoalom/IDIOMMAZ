package com.alom.idiommaz.realmmodel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Contenido extends RealmObject {

    @PrimaryKey
    private int id;
    private String Nivelfk;
    private String Leccionfk;
    private String NomLeccion;
    private int Ultimoindice;

    public Contenido(){
    }

    public Contenido(String nomleccion){
        NomLeccion = nomleccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUltimoindice(int ultimoindice) {
        Ultimoindice = ultimoindice;
    }

    public String getNivelfk() { return Nivelfk; }

    public void setNivelfk(String nivelfk) { Nivelfk = nivelfk; }

    public String getLeccionfk() {
        return Leccionfk;
    }

    public void setLeccionfk(String leccionfk) {
        Leccionfk = leccionfk;
    }

    public int getUltimoindice(){return Ultimoindice;}

    public String getNomLeccion() { return NomLeccion; }

    public void setNomLeccion(String nomleccion) { NomLeccion = nomleccion; }
}
