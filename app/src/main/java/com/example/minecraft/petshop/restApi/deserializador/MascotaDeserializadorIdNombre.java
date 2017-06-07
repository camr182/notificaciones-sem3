package com.example.minecraft.petshop.restApi.deserializador;

import com.example.minecraft.petshop.model.Mascota;
import com.example.minecraft.petshop.restApi.model.JsonKeys;
import com.example.minecraft.petshop.restApi.model.MascotaResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by minecraft on 15/04/2017.
 */

public class MascotaDeserializadorIdNombre implements JsonDeserializer<MascotaResponse> {

    @Override
    public MascotaResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();
        MascotaResponse contactoResponse = gson.fromJson(json,MascotaResponse.class);

        JsonArray contactoResponseData = json.getAsJsonObject().getAsJsonArray(JsonKeys.MEDIA_RESPONSE_ARRAY);

        contactoResponse.setMascotas(deserializarMascotaDeJson(contactoResponseData));

        return contactoResponse;
    }


    private ArrayList<Mascota> deserializarMascotaDeJson(JsonArray contactoResponseData){
        ArrayList<Mascota> mascotas = new ArrayList<>();

        for (int i = 0; i < contactoResponseData.size(); i++) {
            JsonObject contactoResponseDataObject = contactoResponseData.get(i).getAsJsonObject();

            String id = contactoResponseDataObject.get(JsonKeys.USER_ID).getAsString();
            String nombreCompleto = contactoResponseDataObject.get(JsonKeys.USER_FULL_NAME).getAsString();
            String profilePicture = contactoResponseDataObject.get(JsonKeys.USER_PROFILE_PICTURE).getAsString();



            Mascota mascota = new Mascota();
            mascota.setId(id);
            mascota.setNombreCompleto(nombreCompleto);
            mascota.setUrlFoto(profilePicture);


            mascotas.add(mascota);

        }
        return mascotas;
    }

}
