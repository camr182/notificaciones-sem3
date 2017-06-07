package com.example.minecraft.petshop.model;

import android.content.ContentValues;
import android.content.Context;

import com.example.minecraft.petshop.R;
import com.example.minecraft.petshop.bd.BaseDatos;
import com.example.minecraft.petshop.bd.ConstantesBaseDatos;

import java.util.ArrayList;

/**
 * Created by minecraft on 26/03/2017.
 */

public class ConstructorMascotas {


    private static final int LIKE = 1;
    private Context context;
    public ConstructorMascotas(Context context) {
        this.context = context;
    }

    public ArrayList<Mascota> obtenerDatos() {
        BaseDatos db = new BaseDatos(context);

        //validar si ya tiene datos
        if(db.bdLlena()==false){
            ingresarMascotas(db);
        }

        return  db.obtenerTodosMascotas();
    }



    public void ingresarMascotas(BaseDatos db){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Boby");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.mascota1);

        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Brandon");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.mascota2);

        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Pablito");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.mascota3);

        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Max");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.mascota4);

        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Zack");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.mascota5);

        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "El maykol");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.mascota6);

        db.insertarMascota(contentValues);

        contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_NOMBRE, "Tom");
        contentValues.put(ConstantesBaseDatos.TABLE_MASCOTA_FOTO, R.drawable.mascota7);

        db.insertarMascota(contentValues);

    }

    public void darLikeMascota(Mascota mascota){
        BaseDatos db = new BaseDatos(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_LIKES_MASCOTA_ID, mascota.getId());
        contentValues.put(ConstantesBaseDatos.TABLE_LIKES_NUMERO, LIKE);
        db.insertarLikeMascota(contentValues);
    }

    public int obtenerLikesMascota(Mascota mascota){
        BaseDatos db = new BaseDatos(context);
        return db.obtenerLikesMascota(mascota);
    }


}
