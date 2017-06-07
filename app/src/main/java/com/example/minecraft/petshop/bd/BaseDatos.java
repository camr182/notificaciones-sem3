package com.example.minecraft.petshop.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.minecraft.petshop.model.Mascota;

import java.util.ArrayList;

/**
 * Created by minecraft on 26/03/2017.
 */

public class BaseDatos extends SQLiteOpenHelper {

    private Context context;

    public BaseDatos(Context context) {
        super(context, ConstantesBaseDatos.DATABASE_NAME, null, ConstantesBaseDatos.DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String queryCrearTablaContacto = "CREATE TABLE " + ConstantesBaseDatos.TABLE_MASCOTA + "(" +
                ConstantesBaseDatos.TABLE_MASCOTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE + " TEXT, " +
                ConstantesBaseDatos.TABLE_MASCOTA_FOTO + " TEXT " +
                ")";

        String queryCrearTablaLikesContacto = "CREATE TABLE " + ConstantesBaseDatos.TABLE_LIKES + "(" +
                ConstantesBaseDatos.TABLE_LIKES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_LIKES_NUMERO + " INTEGER, " +
                ConstantesBaseDatos.TABLE_LIKES_MASCOTA_ID + " INTEGER, " +
                "FOREIGN KEY (" + ConstantesBaseDatos.TABLE_LIKES_MASCOTA_ID + ") " +
                "REFERENCES " + ConstantesBaseDatos.TABLE_MASCOTA + "(" + ConstantesBaseDatos.TABLE_MASCOTA_ID + ")" +
                ")";

        db.execSQL(queryCrearTablaContacto);
        db.execSQL(queryCrearTablaLikesContacto);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST " + ConstantesBaseDatos.TABLE_MASCOTA);
        db.execSQL("DROP TABLE IF EXIST " + ConstantesBaseDatos.TABLE_LIKES);
        onCreate(db);

    }

    public ArrayList<Mascota> obtenerTodosMascotas() {
        ArrayList<Mascota> mascotas = new ArrayList<>();

        String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_MASCOTA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while (registros.moveToNext()){
            Mascota mascotaActual = new Mascota();
           // mascotaActual.setId(registros.getInt(0));
           // mascotaActual.setNombre(registros.getString(1));
           // mascotaActual.setImagen(registros.getInt(2));

            String queryLikes = "SELECT COUNT("+ConstantesBaseDatos.TABLE_LIKES_NUMERO+") as likes " +
                    " FROM " + ConstantesBaseDatos.TABLE_LIKES +
                    " WHERE " + ConstantesBaseDatos.TABLE_LIKES_MASCOTA_ID + "=" + mascotaActual.getId();

            Cursor registrosLikes = db.rawQuery(queryLikes, null);
            if (registrosLikes.moveToNext()){
                mascotaActual.setLikes(registrosLikes.getInt(0));
            }else {
                mascotaActual.setLikes(0);
            }
            mascotas.add(mascotaActual);

        }

        db.close();

        return mascotas;
    }


    public void insertarMascota(ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_MASCOTA,null, contentValues);
        db.close();

    }

    public void insertarLikeMascota(ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_LIKES, null, contentValues);
        db.close();
    }

    public int obtenerLikesMascota(Mascota mascota){
        int likes = 0;

        String query = "SELECT COUNT("+ConstantesBaseDatos.TABLE_LIKES_NUMERO+")" +
                " FROM " + ConstantesBaseDatos.TABLE_LIKES +
                " WHERE " + ConstantesBaseDatos.TABLE_LIKES_MASCOTA_ID + "="+mascota.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        if (registros.moveToNext()){
            likes = registros.getInt(0);
        }

        db.close();
        return likes;
    }

    public boolean bdLlena(){

        String query = "SELECT * FROM "+ConstantesBaseDatos.TABLE_MASCOTA; ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        if (registros.moveToFirst()==false){
            return false;
        }

        return true;
    }


}
