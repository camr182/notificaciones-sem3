package com.example.minecraft.petshop.restApi.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;




/**
 * Created by minecraft on 15/04/2017.
 */

public interface EndpointsApi {

    @GET(ConstantesRestApi.URL_RECENT_MEDIA_USER)
    public Call<MascotaResponse> getUserRecentMedia(@Path("user_id") String userId,
                                                    @Query("access_token") String accessToken);

    @GET(ConstantesRestApi.URL_SEARCH_USERS)
    public Call<MascotaResponse> search(@Query("q") String query, @Query("access_token") String accessToken);


    @FormUrlEncoded
    @POST(ConstantesRestApi.KEY_POST_ID_USUARIO)
    retrofit2.Call<UsuarioResponse> setUsuarioInstagram(@Field("id_dispositivo") String id_dispositivo,
                                                   @Field("id_usuario_instagram") String id_usuario_instagram);

    @FormUrlEncoded
    @POST(ConstantesRestApi.KEY_SET_LIKE)
    Call<MediaLikeResponse> setLikeMedia (@Path("media-id") String id_foto_instagram,@Field("access_token") String access_token);

}
