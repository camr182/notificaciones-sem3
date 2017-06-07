package com.example.minecraft.petshop.restApiHeroku.adapter;

import com.example.minecraft.petshop.restApiHeroku.ConstantesRestApiHeroku;
import com.example.minecraft.petshop.restApiHeroku.model.EndpointsHeroku;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by minecraft on 23/05/2017.
 */

public class RestApiHerokuAdapter {

    public EndpointsHeroku conexionRestAPIHeroku(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApiHeroku.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                ;
        return retrofit.create(EndpointsHeroku.class);
    }
}
