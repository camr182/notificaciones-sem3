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

public class MascotaDeserializadorMediaRecent implements JsonDeserializer<MascotaResponse> {



    @Override
    public MascotaResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {


        Gson gson = new Gson();
        MascotaResponse mascotaResponse = gson.fromJson(json, MascotaResponse.class);
        JsonArray mascotaResponseData = json.getAsJsonObject().getAsJsonArray(JsonKeys.MEDIA_RESPONSE_ARRAY);

        mascotaResponse.setMascotas(deserializarMascotaDeJson(mascotaResponseData));
        return mascotaResponse;

    }

    private ArrayList<Mascota> deserializarMascotaDeJson(JsonArray contactoResponseData){
        ArrayList<Mascota> mascotas = new ArrayList<>();
        for (int i = 0; i < contactoResponseData.size() ; i++) {
            JsonObject contactoResponseDataObject = contactoResponseData.get(i).getAsJsonObject();

            JsonObject userJson     = contactoResponseDataObject.getAsJsonObject(JsonKeys.USER);
            String id               = userJson.get(JsonKeys.USER_ID).getAsString();
            String nombreCompleto   = userJson.get(JsonKeys.USER_FULL_NAME).getAsString();
            String urlFotoPerfil    = userJson.get(JsonKeys.USER_PROFILE_PICTURE).getAsString();

            JsonObject imageJson            = contactoResponseDataObject.getAsJsonObject(JsonKeys.MEDIA_IMAGES);
            JsonObject stdResolutionJson    = imageJson.getAsJsonObject(JsonKeys.MEDIA_STANDARD_RESOLUTIONS);
            String urlFoto                  = stdResolutionJson.get(JsonKeys.MEDIA_URL).getAsString();

            JsonObject likesJson = contactoResponseDataObject.getAsJsonObject(JsonKeys.MEDIA_LIKES);
            int likes = likesJson.get(JsonKeys.MEDIA_LIKES_COUNT).getAsInt();

            String idImagen = contactoResponseDataObject.get(JsonKeys.MEDIA_ID).getAsString();

            Mascota contactoActual = new Mascota();
            contactoActual.setId(id);
            contactoActual.setNombreCompleto(nombreCompleto);
            contactoActual.setUrlFotoPerfil(urlFotoPerfil);
            contactoActual.setUrlFoto(urlFoto);
            contactoActual.setLikes(likes);
            contactoActual.setIdImagen(idImagen);


            mascotas.add(contactoActual);

        }

        return mascotas;
    }


}
