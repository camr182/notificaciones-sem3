package com.example.minecraft.petshop.restApiHeroku.model;

import com.example.minecraft.petshop.model.Mascota;

import java.util.ArrayList;

/**
 * Created by minecraft on 23/05/2017.
 */

public class MediaLikeNotifResponse {

    ArrayList<MediaLike> mediaLike;

    public ArrayList<MediaLike> getMediaLike() {
        return mediaLike;
    }

    public void setMediaLike(ArrayList<MediaLike> mediaLike) {
        this.mediaLike = mediaLike;
    }
}
