package com.example.minecraft.petshop.restApi.adapter;

import com.example.minecraft.petshop.restApi.deserializador.MascotaDeserializadorMediaRecent;
import com.example.minecraft.petshop.restApi.deserializador.MascotaDeserializadorIdNombre;
import com.example.minecraft.petshop.restApi.deserializador.MediaLikeDeserializador;
import com.example.minecraft.petshop.restApi.model.ConstantesRestApi;
import com.example.minecraft.petshop.restApi.model.EndpointsApi;
import com.example.minecraft.petshop.restApi.model.MascotaResponse;
import com.example.minecraft.petshop.restApi.model.MediaLikeResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by minecraft on 15/04/2017.
 */

public class RestApiAdapter {

    public EndpointsApi establecerConexionRestApiInstagram(Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(EndpointsApi.class);
    }


    public EndpointsApi establecerConexionRestHeroku(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL_HEROKU)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(EndpointsApi.class);
    }


    public Gson construyeGsonDeserializadorMediaRecent(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MascotaResponse.class, new MascotaDeserializadorMediaRecent());
        return gsonBuilder.create();
    }

    public Gson construyeGsonDeserializadorIdPorNombre(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MascotaResponse.class, new MascotaDeserializadorIdNombre());
        return gsonBuilder.create();
    }

    public Gson construyeGsonDeserializadorSetLikeMedia(){
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(MediaLikeResponse.class,new MediaLikeDeserializador());

        return gsonBuilder.create();
    }

}
