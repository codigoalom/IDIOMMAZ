package com.alm.idiommaz220;

import android.util.Log;

public class Contenido {
    String ID;
    String Nivelfk;
    String Leccionfk;
    String[] Cont;
    String NomLeccion;
    int Ultimoindice;

    public Contenido(){
    }

    public Contenido(String nomleccion, String[] cont){
        NomLeccion = nomleccion;
        Cont = cont;
    }

    public void setID(String id){
        ID = id;
    }

    public String getID(){
        return ID;
    }

    public String getNivelfk() { return Nivelfk; }

    public void setNivelfk(String nivelfk) { Nivelfk = nivelfk; }

    public String getLeccionfk() {
        return Leccionfk;
    }

    public void setLeccionfk(String leccionfk) {
        Leccionfk = leccionfk;
    }

    public String sortCont() {
        String res = "";
        int i;
        for (i=0; i<Cont.length; i++)
            res += Integer.toString(i) + " " + Cont[i] + " ";
        Ultimoindice = i;
        return res + Integer.toString(i);
    }

    public int getUltimoindice(){return Ultimoindice;}

    public void setCont(String[] cont) { Cont = cont; }

    public String getNomLeccion() { return NomLeccion; }

    public void setNomLeccion(String nomleccion) { NomLeccion = nomleccion; }
}
