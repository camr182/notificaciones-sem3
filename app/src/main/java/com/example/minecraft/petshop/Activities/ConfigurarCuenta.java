package com.example.minecraft.petshop.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.minecraft.petshop.MainActivity;
import com.example.minecraft.petshop.R;
import com.example.minecraft.petshop.model.Mascota;
import com.example.minecraft.petshop.restApi.adapter.RestApiAdapter;
import com.example.minecraft.petshop.restApi.model.ConstantesRestApi;
import com.example.minecraft.petshop.restApi.model.EndpointsApi;
import com.example.minecraft.petshop.restApi.model.JsonKeys;
import com.example.minecraft.petshop.restApi.model.MascotaResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigurarCuenta extends AppCompatActivity {

    private static final String TAG = ConfigurarCuenta.class.getName();
    TextInputEditText nombreContacto;
    private ArrayList<Mascota> mascotas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_cuenta);

        nombreContacto = (TextInputEditText)findViewById(R.id.inNombreCuenta);
        String contacto = nombreContacto.getText().toString();
        //ConstantesRestApi constantesRestApi = new ConstantesRestApi(contacto, nombreUser);

    }


    public void buscarContacto(final View view) {

        String contacto = nombreContacto.getText().toString();

        RestApiAdapter raa = new RestApiAdapter();
        Gson gsonSearch = raa.construyeGsonDeserializadorIdPorNombre();
        EndpointsApi epa = raa.establecerConexionRestApiInstagram(gsonSearch);

        final Call<MascotaResponse> mascotaResponseCall = epa.search(contacto, ConstantesRestApi.ACCESS_TOKEN);

        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {

                ArrayList<Mascota> mascotas = response.body().getMascotas();

                if (mascotas != null && !mascotas.isEmpty()) {

                    guardarPreferenciasUsuario(response.body().getMascotas().get(0));
                    Intent intent = new Intent(ConfigurarCuenta.this, MainActivity.class);
                    startActivity(intent);
                    ActivityCompat.finishAffinity(ConfigurarCuenta.this);
                }else{
                    Snackbar.make(view,"No se encontró el usuario ingresado.",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(ConfigurarCuenta.this, "Fallo conexion, intente de nuevo.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Fallo conexion: " + t.toString());
            }
        });


    }


    public void guardarPreferenciasUsuario(Mascota mascota) {

        SharedPreferences preps = getSharedPreferences("datosPersonales", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preps.edit();

        String profilePicture = mascota.getUrlFotoPerfil();
        String nombre = mascota.getNombreCompleto();
        String idUsuario = mascota.getId();

        edit.putString(JsonKeys.USER_ID, idUsuario);
        edit.putString(JsonKeys.USER_FULL_NAME, nombre);
        edit.putString(JsonKeys.USER_PROFILE_PICTURE, profilePicture);

        edit.commit();

    }

}
