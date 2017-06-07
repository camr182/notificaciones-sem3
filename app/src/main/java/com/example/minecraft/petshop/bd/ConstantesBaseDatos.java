package com.example.minecraft.petshop.bd;

/**
 * Created by minecraft on 26/03/2017.
 */

public class ConstantesBaseDatos {

    public static final String DATABASE_NAME = "mascotas";
    public static final int DATABASE_VERSION = 1;


    public static final String TABLE_MASCOTA           = "mascota";

    public static final String TABLE_MASCOTA_ID        = "id_mascota";
    public static final String TABLE_MASCOTA_NOMBRE    = "nombre_mascota";
    public static final String TABLE_MASCOTA_FOTO      = "foto_mascota";


    public static final String TABLE_LIKES = "likes";

    public static final String TABLE_LIKES_ID = "id_likes";
    public static final String TABLE_LIKES_MASCOTA_ID = "id_mascota";
    public static final String TABLE_LIKES_NUMERO = "numero_likes";

}
