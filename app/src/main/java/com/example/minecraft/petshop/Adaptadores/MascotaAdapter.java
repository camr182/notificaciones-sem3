package com.example.minecraft.petshop.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minecraft.petshop.Activities.DetalleMascota;
import com.example.minecraft.petshop.model.Mascota;
import com.example.minecraft.petshop.R;
import com.example.minecraft.petshop.restApi.adapter.RestApiAdapter;
import com.example.minecraft.petshop.restApi.model.ConstantesRestApi;
import com.example.minecraft.petshop.restApi.model.EndpointsApi;
import com.example.minecraft.petshop.restApiHeroku.adapter.RestApiHerokuAdapter;
import com.example.minecraft.petshop.restApiHeroku.model.EndpointsHeroku;
import com.example.minecraft.petshop.restApiHeroku.model.MediaLike;
import com.example.minecraft.petshop.restApiHeroku.model.MediaLikeNotifResponse;
import com.example.minecraft.petshop.restApi.model.MediaLikeResponse;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by minecraft on 12/03/2017.
 */

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {

    private ArrayList<Mascota> items;
    Activity activity;
    private String id_usuario_instagram;
    private String id_dispositivo;


    public MascotaAdapter(ArrayList<Mascota> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }

    public static class MascotaViewHolder extends RecyclerView.ViewHolder {

        public ImageView foto;
        public TextView nombre;
        private TextView tvLikes;
        public ImageButton btnLike;

        public MascotaViewHolder(View itemView) {
            super(itemView);

            foto = (ImageView) itemView.findViewById(R.id.ivFoto);
            nombre = (TextView) itemView.findViewById(R.id.tvNombre);
            tvLikes = (TextView) itemView.findViewById(R.id.tvLike);
            btnLike = (ImageButton) itemView.findViewById(R.id.ibtnLike);

        }
    }

    private void obtieneSharedpreferences() {
        SharedPreferences miCuentaPref = activity.getSharedPreferences("MiCuenta", Context.MODE_PRIVATE);
        id_dispositivo = miCuentaPref.getString("idDispositivo", null);
    }



    @Override
    public MascotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_mascota,parent,false);
        obtieneSharedpreferences();
        return new MascotaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MascotaViewHolder holder, final int position) {

        final Mascota mascota = items.get(position);
        //ConstructorMascotas constructorMascotas = new ConstructorMascotas(activity);

        Picasso.with(activity)
                .load(mascota.getUrlFoto())
                .placeholder(R.drawable.fondo_imagen)
                .into(holder.foto);

        holder.tvLikes.setText(String.valueOf(mascota.getLikes()));
        //holder.foto.setImageResource(items.get(position).getImagen());
       // holder.nombre.setText(items.get(position).getNombre());
        //holder.tvLikes.setText(constructorMascotas.obtenerLikesMascota(mascota));
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RestApiAdapter restApiAdapter = new RestApiAdapter();
                Gson gsonMediaLike = restApiAdapter.construyeGsonDeserializadorSetLikeMedia();
                EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaLike);
                Call<MediaLikeResponse> mediaLikeResponseCall = endpointsApi.setLikeMedia(items.get(position).getIdImagen(), ConstantesRestApi.ACCESS_TOKEN);
                mediaLikeResponseCall.enqueue(new Callback<MediaLikeResponse>() {
                    @Override
                    public void onResponse(Call<MediaLikeResponse> call, Response<MediaLikeResponse> response) {
                        MediaLikeResponse mediaLikeResponse = response.body();
                        if (mediaLikeResponse.getCode() == 200) {
                            items.get(position).sumaLike();
                            if(id_dispositivo == null){
                                Toast.makeText(activity, "Se requiere activar la recepcion de notificaciones", Toast.LENGTH_LONG).show();

                            }else{
                                enviaNotificacionMediaLike(items.get(position).getIdImagen(),items.get(position).getId());

                            }

                            holder.tvLikes.setText(String.valueOf(items.get(position).getLikes()));
                            Toast.makeText(activity, "Diste Like", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "Error intenta mas tarde", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MediaLikeResponse> call, Throwable t) {
                        Toast.makeText(activity, "Error intenta mas tarde", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        holder.foto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),mascota.getNombre(),Toast.LENGTH_SHORT).show();

                Intent i = new Intent(v.getContext(), DetalleMascota.class);
                i.putExtra("url",mascota.getUrlFoto());
                i.putExtra("likes",mascota.getLikes());
               // i.putExtra("imagen",mascota.getImagen());
                v.getContext().startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void enviaNotificacionMediaLike(String id_foto_instagram,String id_usuario_instagram) {
        Log.d("Inicio","enviaNotoficacionMediaLike");
        MediaLike mediaLikeNotifResponse = new MediaLike(id_foto_instagram,id_usuario_instagram,id_dispositivo);
        RestApiHerokuAdapter restApiHerokuAdapter = new RestApiHerokuAdapter();
        EndpointsHeroku endpointsHeroku = restApiHerokuAdapter.conexionRestAPIHeroku();
        Call<MediaLikeNotifResponse> mediaLikeNotifResponseCall = endpointsHeroku.enviaNotificacionLike(mediaLikeNotifResponse.getId_foto_instagram(),
                mediaLikeNotifResponse.getId_usuario_instagram(),mediaLikeNotifResponse.getId_dispositivo());
        mediaLikeNotifResponseCall.enqueue(new Callback<MediaLikeNotifResponse>() {
            @Override
            public void onResponse(Call<MediaLikeNotifResponse> call, Response<MediaLikeNotifResponse> response) {
                MediaLikeNotifResponse mediaLikeNotifResponse1 = response.body();
                Toast.makeText(activity, "Notificacion Enviada", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<MediaLikeNotifResponse> call, Throwable t) {
                Toast.makeText(activity, "Notificacion No Enviada", Toast.LENGTH_LONG).show();
            }
        });
    }




}
