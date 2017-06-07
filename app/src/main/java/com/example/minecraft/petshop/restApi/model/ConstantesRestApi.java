package com.example.minecraft.petshop.restApi.model;

/**
 * Created by minecraft on 15/04/2017.
 */

public class ConstantesRestApi {



    //https://api.instagram.com/v1/
    public static final String VERSION = "/v1/";
    public static final String ROOT_URL = "https://api.instagram.com" + VERSION;
    public static final String ACCESS_TOKEN = "4991213468.ea9ba33.98ae13a20e8342cebe926f3bc6d765db";

    public static final String URL_RECENT_MEDIA_USER = "users/{user_id}/media/recent/";
    public static final String KEY_SET_LIKE = "media/{media-id}/likes";
    //https://api.instagram.com/v1/users/self/media/recent/?access_token=ACCESS-TOKEN

    public static final String URL_SEARCH_USERS = "users/search";

    //server heroku

    public static final String ROOT_URL_HEROKU = "https://shrouded-everglades-58752.herokuapp.com/";
    public static final String KEY_POST_ID_TOKEN = "token-device/";
    public static final String KEY_POST_ID_USUARIO = "registrar-usuario/";


}
