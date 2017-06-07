package com.example.minecraft.petshop.restApi.model;

/**
 * Created by minecraft on 23/05/2017.
 */

public class MediaLikeResponse {

    private String id_foto_instagram;
    private int code;

    public MediaLikeResponse() {
    }

    public String getId_foto_instagram() {
        return id_foto_instagram;
    }

    public void setId_foto_instagram(String id_foto_instagram) {
        this.id_foto_instagram = id_foto_instagram;
    }

    public MediaLikeResponse(String id_foto_instagram, int code) {
        this.id_foto_instagram = id_foto_instagram;
        this.code = code;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
