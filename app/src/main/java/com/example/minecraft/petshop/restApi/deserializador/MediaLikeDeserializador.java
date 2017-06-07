package com.example.minecraft.petshop.restApi.deserializador;

import com.example.minecraft.petshop.restApi.model.JsonKeys;
import com.example.minecraft.petshop.restApi.model.MediaLikeResponse;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by minecraft on 23/05/2017.
 */

public class MediaLikeDeserializador implements JsonDeserializer<MediaLikeResponse> {

    @Override
    public MediaLikeResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        MediaLikeResponse mediaLikeResponse = gson.fromJson(json,MediaLikeResponse.class);
        JsonObject metaResponseData = json.getAsJsonObject().getAsJsonObject(JsonKeys.LIKE_META);
        int code = metaResponseData.get(JsonKeys.LIKE_CODE).getAsInt();
        mediaLikeResponse.setCode(code);
        mediaLikeResponse.setId_foto_instagram("");

        return mediaLikeResponse;
    }
}