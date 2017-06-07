package com.example.minecraft.petshop.restApi.model;

/**
 * Created by minecraft on 15/04/2017.
 */

public class JsonKeys {

    public static final String MEDIA_RESPONSE_ARRAY              ="data";  // array de objetos

    public static final String USER                              ="user";  //objeto padre
    public static final String USER_ID                           ="id";
    public static final String USER_FULL_NAME                    ="full_name";
    public static final String USER_PROFILE_PICTURE              ="profile_picture";


    public static final String MEDIA_IMAGES                      ="images";  //objeto padre
    public static final String MEDIA_STANDARD_RESOLUTIONS        ="standard_resolution";  //objeto hijo
    public static final String MEDIA_URL                         ="url"; //parameto objeto hijo

    public static final String MEDIA_LIKES                       ="likes";
    public static final String MEDIA_LIKES_COUNT                 ="count";

    //media like
    public static final String LIKE_META                = "meta";
    public static final String LIKE_CODE                = "code";
    public static final String MEDIA_ID                 = "id";






}
