package com.example.minecraft.petshop.presentador;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.minecraft.petshop.Adaptadores.MascotaAdapter;
import com.example.minecraft.petshop.MainActivity;
import com.example.minecraft.petshop.R;
import com.example.minecraft.petshop.model.ConstructorMascotas;
import com.example.minecraft.petshop.model.Mascota;
import com.example.minecraft.petshop.restApi.adapter.RestApiAdapter;
import com.example.minecraft.petshop.restApi.model.ConstantesRestApi;
import com.example.minecraft.petshop.restApi.model.EndpointsApi;
import com.example.minecraft.petshop.restApi.model.JsonKeys;
import com.example.minecraft.petshop.restApi.model.MascotaResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by minecraft on 26/03/2017.
 */

public class Fragment_listado_mascotasPresenter implements IFragment_listado_mascotasPresenter {


    //private IFragment_listado_mascotasPresenter iFragment_listado_mascotasPresenter;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private GridLayoutManager lManager;
    private Context context;
    private ConstructorMascotas constructorMascotas;
    private ArrayList<Mascota> mascotas;
    private View v;
    private Activity activity;

    public  Fragment_listado_mascotasPresenter(Context context, View v, Activity activity) {
       // this.iRecyclerViewFragmentView = iRecyclerViewFragmentView;
        this.context = context;
        this.v=v;
        this.activity=activity;
        //obtenerMascotas();
        obtenerMediosRecientes();
    }

    @Override
    public void obtenerMascotas() {
        constructorMascotas = new ConstructorMascotas(context);
        mascotas = constructorMascotas.obtenerDatos();
        //mostrarMacotasRV();

        //ArrayList<Mascota> items = new ArrayList<>();
        // Obtener el Recycler

        mostrarMascotasRV();

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
                SharedPreferences nombreUsuarioInstagram = activity.getSharedPreferences("datos",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = nombreUsuarioInstagram.edit();
                editor.putString("nombreUsuario",mascotas.get(0).getNombreCompleto());
                editor.putString("idUsuario",mascotas.get(0).getId());
                editor.commit();
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


        recycler = (RecyclerView) v.findViewById(R.id.rvMascotas);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        //lManager = new GridLayoutManager(this,1);
        lManager = new GridLayoutManager(context,1);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new MascotaAdapter(mascotas,activity);
        recycler.setAdapter(adapter);

    }
}
