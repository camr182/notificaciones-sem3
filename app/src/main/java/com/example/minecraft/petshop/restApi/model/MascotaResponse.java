package com.example.minecraft.petshop.restApi.model;

import com.example.minecraft.petshop.model.Mascota;

import java.util.ArrayList;

/**
 * Created by minecraft on 15/04/2017.
 */

public class MascotaResponse {

    ArrayList<Mascota> mascotas;

    public ArrayList<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(ArrayList<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
}
