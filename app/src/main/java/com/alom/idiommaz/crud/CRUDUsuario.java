package com.alom.idiommaz.crud;

import android.util.Log;

import com.alom.idiommaz.realmmodel.Usuario;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class CRUDUsuario {

    private final static int calculateId(){
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(Usuario.class).max("id");
        int NextId;
        if(currentIdNum == null){
            NextId = 0;
        }else {
            NextId = currentIdNum.intValue()+1;
        }
        return NextId;
    }

    public final static void addUsuario(final Usuario usuario){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                int index = CRUDUsuario.calculateId();
                Usuario realmUsuario = realm.createObject(Usuario.class, index);
                realmUsuario.setNombre(usuario.getNombre());
            }
        });
    }

    public final static List<Usuario> getAllUsuarios(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Usuario> usuarios = realm.where(Usuario.class).findAll();
        for (Usuario usuario: usuarios){
            Log.d("Main", "id " + usuario.getId() + " Nombre del usuario " + usuario.getNombre());
        }
        return usuarios;
    }

    public final static Usuario getUsuarioByName(String nombre){
        Realm realm = Realm.getDefaultInstance();
        Usuario usuario = realm.where(Usuario.class).equalTo("nombre", nombre).findFirst();
        if(usuario!=null){
            Log.d("Main", "id " + usuario.getId() + " Nombre del usuario " + usuario.getNombre());
        }
        return usuario;
    }

    public final static Usuario getUsuarioById(int id){
        Realm realm = Realm.getDefaultInstance();
        Usuario usuario = realm.where(Usuario.class).equalTo("id", id).findFirst();
        if(usuario!=null){
            Log.d("Main", "id " + usuario.getId() + " Nombre del usuario " + usuario.getNombre());
        }
        return usuario;
    }

    public final static void updateUsuarioById(int id){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Usuario usuario = realm.where(Usuario.class).equalTo("id", id).findFirst();
        usuario.setNombre("Roberto");
        realm.insertOrUpdate(usuario);
        realm.commitTransaction();
        if(usuario!=null){
            Log.d("Main", "id " + usuario.getId() + " Nombre del usuario " + usuario.getNombre());
        }
    }

    public final static void deleteUsuarioById(int id){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Usuario usuario = realm.where(Usuario.class).equalTo("id", id).findFirst();
        usuario.deleteFromRealm();
        realm.commitTransaction();
    }

    public final static void deleteAllUsuarios(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Usuario> usuarios = realm.where(Usuario.class).findAll();
        usuarios.deleteAllFromRealm();
        realm.commitTransaction();
    }
}
