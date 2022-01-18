package com.municoyllur.servicioscoyllurquiapp.Utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SampleSQLiteDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "sample_database";
    public static final String COMUNICADO_TABLE_NAME = "Comunicado";
    public static final String co_idComunicado = "idComunicado";
    public static final String co_TipoComunicado = "TipoComunicado";
    public static final String co_objetivoComunicado = "objetivoComunicado";
    public static final String co_titulo = "titulo";
    public static final String co_descripcion = "descripcion";
    public static final String co_link = "link";
    public static final String co_fechaComunicado = "fechaComunicado";

    public SampleSQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + COMUNICADO_TABLE_NAME + " (" +
                co_idComunicado + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                co_TipoComunicado + " INTEGER, " +
                co_objetivoComunicado + " INTEGER , " +
                co_titulo + " TEXT , " +
                co_descripcion + " TEXT , " +
                co_link + " TEXT , " +
                co_fechaComunicado + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + COMUNICADO_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}