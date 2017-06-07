package com.example.minecraft.petshop.presentador;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minecraft.petshop.Adaptadores.MascotaPerfilAdapter;
import com.example.minecraft.petshop.R;
import com.example.minecraft.petshop.model.Mascota;
import com.example.minecraft.petshop.restApi.adapter.RestApiAdapter;
import com.example.minecraft.petshop.restApi.model.ConstantesRestApi;
import com.example.minecraft.petshop.restApi.model.EndpointsApi;
import com.example.minecraft.petshop.restApi.model.JsonKeys;
import com.example.minecraft.petshop.restApi.model.MascotaResponse;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by minecraft on 15/04/2017.
 */

public class Fragment_perfil_mascotaPresenter implements IFragment_listado_mascotasPresenter {


    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private GridLayoutManager lManager;
    public TextView nombrePerfil;
    public CircularImageView imagenPerfil;
    ArrayList<Mascota> mascotas;
    private Context context;
    private Activity activity;
    private View v;

    public Fragment_perfil_mascotaPresenter(Context context, Activity activity, View v) {
        this.context = context;
        this.activity = activity;
        this.v = v;
        obtenerMediosRecientes();

    }


    @Override
    public void obtenerMascotas() {

    }

    @Override
    public void obtenerMediosRecientes() {

        SharedPreferences preps = context.getSharedPreferences("datosPersonales", Context.MODE_PRIVATE);
        String idUsuario = preps.getString(JsonKeys.USER_ID, "5075864862");

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorMediaRecent();
        EndpointsApi endpointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaRecent);
        Call<MascotaResponse> contactoResponseCall = endpointsApi.getUserRecentMedia(idUsuario, ConstantesRestApi.ACCESS_TOKEN);

        contactoResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                MascotaResponse contactoResponse = response.body();
                mascotas = contactoResponse.getMascotas();
                mostrarMascotasRV();
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(context, "¡Algo pasó en la conexión! Intenta de nuevo", Toast.LENGTH_LONG).show();
                Log.e("FALLO LA CONEXION", t.toString());
            }
        });

    }



    @Override
    public void mostrarMascotasRV() {

        nombrePerfil = (TextView) activity.findViewById(R.id.tvNombrePerfil);
        imagenPerfil = (CircularImageView) activity.findViewById(R.id.circularImageView);
        final Mascota mascota = mascotas.get(0);

        //ConstructorMascotas constructorMascotas = new ConstructorMascotas(activity);

        Picasso.with(activity)
                .load(mascota.getUrlFotoPerfil())
                .placeholder(R.drawable.fondo_imagen)
                .into(imagenPerfil);

        nombrePerfil.setText(String.valueOf(mascota.getNombreCompleto()));

        recycler = (RecyclerView) v.findViewById(R.id.rvMascotasPerfil);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        //lManager = new GridLayoutManager(this,1);
        lManager = new GridLayoutManager(context,2);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new MascotaPerfilAdapter(mascotas,activity);
        recycler.setAdapter(adapter);

    }



}
