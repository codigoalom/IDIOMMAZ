package com.alom.idiommaz.realmmodel;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Usuario extends RealmObject {

    @PrimaryKey
    private int id;
    private String nombre;
    private String contvisto;
    private RealmList<Nivel> nivelesvistos;
    private RealmList<Leccion> leccionesvistas;
    private RealmList<Expresion> expresionesvistas;

    public Usuario(){

    }

    public Usuario(String nomusuario, String contvisto){
        nombre = nomusuario;
        contvisto = contvisto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<Nivel> getNivelesvistos() {
        return nivelesvistos;
    }

    public void setNivelesvistos(RealmList<Nivel> nivelesvistos) {
        this.nivelesvistos = nivelesvistos;
    }

    public RealmList<Leccion> getLeccionesvistas() {
        return leccionesvistas;
    }

    public void setLeccionesvistas(RealmList<Leccion> leccionesvistas) {
        this.leccionesvistas = leccionesvistas;
    }

    public RealmList<Expresion> getExpresionesvistas() {
        return expresionesvistas;
    }

    public void setExpresionesvistas(RealmList<Expresion> expresionesvistas) {
        this.expresionesvistas = expresionesvistas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nomUsuario) {
        nombre = nomUsuario;
    }

    public String getContVisto() {
        return contvisto;
    }

    public void setContVisto(String contVisto) {
        contvisto = contVisto;
    }
}
