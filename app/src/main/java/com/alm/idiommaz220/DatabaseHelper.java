package com.alm.idiommaz220;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "idiommaz.db";

    //Tabla de Niveles
    public static final String TABLE_NIVEL = "nivel_table";
    public static final String COL_NIVEL_ID = "ID";
    public static final String COL_NIVEL = "nivel";

    //Tabla de Lecciones
    public static final String TABLE_LECCION = "leccion_table";
    public static final String COL_LECCION_ID = "ID";
    public static final String COL_NOMBRE_LECCION = "nombre_leccion";
    public static final String COL_NIVEL_FK = "nivel_fk";

    //Tabla de Contenido de cada Lección
    public static final String TABLE_CONTENIDO = "contenido_table";
    public static final String COL_CONTENIDO_ID = "ID";
    public static final String COL_CONTENIDO = "contenido";
    public static final String COL_LECCION_FK = "leccion_fk";
    public static final String COL_ULTIMOINDICE = "ultimoindice";
    public static final String COL_NIVELENCONT_FK = "nivel_fk";

    //Tabla información usuario
    public static final String TABLE_USUARIO = "usuario_table";
    public static final String COL_USUARIO_ID = "ID";
    public static final String COL_NOMUSUARIO = "nombre_usuario";
    public static final String COL_CONTENIDOVISTO = "contenido_visto";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 15);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crea Tabla Nivel
        String CREATE_TBL_NIVEL = ("CREATE TABLE " + TABLE_NIVEL +
                "(" + COL_NIVEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NIVEL + " TEXT)"
        );

        //Crea Tabla Lección
        String CREATE_TBL_LECCION = ("CREATE TABLE " + TABLE_LECCION +
                "(" + COL_LECCION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NOMBRE_LECCION +
                " TEXT," + COL_NIVEL_FK + " TEXT)"
        );

        //Crea Tabla Contenido
        String CREATE_TBL_CONTENIDO = ("CREATE TABLE " + TABLE_CONTENIDO +
                "(" + COL_CONTENIDO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_CONTENIDO +
                " TEXT, " + COL_LECCION_FK + " TEXT, " + COL_ULTIMOINDICE + " TEXT, " +
                COL_NIVELENCONT_FK + " TEXT)"
        );

        //Crea Tabla Usuario (futuro, más adelante: separar nombres de usuario en una tabla
        //diferente de la tabla de información. Asignar usuariosfk a la tabla de info
        String CREATE_TBL_USUARIO = ("CREATE TABLE " + TABLE_USUARIO +
                "(" + COL_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NOMUSUARIO +
                " TEXT, " + COL_CONTENIDOVISTO + " TEXT)"
        );

        db.execSQL(CREATE_TBL_NIVEL);
        db.execSQL(CREATE_TBL_LECCION);
        db.execSQL(CREATE_TBL_CONTENIDO);
        db.execSQL(CREATE_TBL_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Clear table data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NIVEL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LECCION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTENIDO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);

        //Recreate table
        onCreate(db);
    }

    public void deleteValuesFromTable(String tablename){
        String TABLENAME = tablename;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLENAME);
    }


    public boolean insertNIVEL(Nivel nivel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COL_NIVEL, nivel.getNivel());

        long row_id_or_failed_to_insert = db.insert(TABLE_NIVEL, null, contentvalues);

        if(row_id_or_failed_to_insert == -1)
            return false;
        else
            return true;
    }

    //Obtener listado de Niveles
    public Cursor getNiveles(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NIVEL, null);
        return res;
    }

    //Obtener ID de nivel
    public String getIDFromNivel(String niv){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NIVEL, new String[]{});

        if(res != null){
            res.moveToFirst();
        }

        StringBuilder builder = new StringBuilder();

        for(int l=0; l<res.getCount(); l++){
            String id = res.getString(0);
            String nivel = res.getString(1);
            if(nivel.trim().equalsIgnoreCase(niv.trim())){
                builder.append(id);
            }
            res.moveToNext();
        }

        return builder.toString();
    }

    public boolean insertLeccion(Leccion leccion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COL_NOMBRE_LECCION, leccion.getNomLeccion());
        contentvalues.put(COL_NIVEL_FK, leccion.getNivelFkEnCont());

        long row_id_or_failed_to_insert = db.insert(TABLE_LECCION, null, contentvalues);

        if(row_id_or_failed_to_insert == -1)
            return false;
        else
            return true;
    }

    //Obtener listado de Lecciones
    public Cursor getLecciones(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_LECCION, null);
        return res;
    }

    public boolean insertContenido(Contenido contenido){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COL_CONTENIDO, contenido.sortCont());
        contentvalues.put(COL_LECCION_FK, contenido.getLeccionfk());
        contentvalues.put(COL_ULTIMOINDICE, contenido.getUltimoindice());
        contentvalues.put(COL_NIVELENCONT_FK, contenido.getNivelfk());

        long row_id_or_failed_to_insert = db.insert(TABLE_CONTENIDO, null, contentvalues);

        if(row_id_or_failed_to_insert == -1)
            return false;
        else
            return true;
    }

    //Obtener listado de Contenidos
    public Cursor getContenidos(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_CONTENIDO, null);
        return res;
    }

    public boolean insertUsuario(Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(COL_NOMUSUARIO, usuario.getNomUsuario());
        contentvalues.put(COL_CONTENIDOVISTO, usuario.getContVisto());

        long row_id_or_failed_to_insert = db.insert(TABLE_USUARIO, null, contentvalues);

        if(row_id_or_failed_to_insert == -1)
            return false;
        else
            return true;
    }

    public Cursor getUsuarios(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_USUARIO, null);
        return res;
    }

    public void updateIdiommaz(String tblname, String newvaluecolname, String newvalue,
                               String wherecolumn, String[] wherevalues){
        SQLiteDatabase idiommazDbWritable = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(newvaluecolname, newvalue);

        idiommazDbWritable.update(tblname, values, wherecolumn+" = ?", wherevalues);
    }
}
