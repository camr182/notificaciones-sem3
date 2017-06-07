package com.example.minecraft.petshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minecraft.petshop.Activities.ConfigurarCuenta;
import com.example.minecraft.petshop.Activities.Contacto;
import com.example.minecraft.petshop.Adaptadores.PageAdapter;
import com.example.minecraft.petshop.model.Mascota;
import com.example.minecraft.petshop.restApi.adapter.RestApiAdapter;
import com.example.minecraft.petshop.restApi.model.ConstantesRestApi;
import com.example.minecraft.petshop.restApi.model.EndpointsApi;
import com.example.minecraft.petshop.restApi.model.MascotaResponse;
import com.example.minecraft.petshop.restApi.model.UsuarioResponse;
import com.example.minecraft.petshop.vista.fragments.Fragment_listado_mascotas;
import com.example.minecraft.petshop.vista.fragments.Fragment_perfil_mascota;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<Mascota> mascota;
    TextView nombrePerfil;
    String nombreUsuario;


    /*public MainActivity(ArrayList<Mascota> mascota) {
        this.mascota = mascota;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Animes

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        setUpViewPager();

    }



    private ArrayList<android.support.v4.app.Fragment> agregarFragments (){

        ArrayList<android.support.v4.app.Fragment> fragments = new ArrayList<>();

        fragments.add(new Fragment_perfil_mascota());
        fragments.add(new Fragment_listado_mascotas());



        return fragments;

    }


    private void setUpViewPager (){

        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),agregarFragments()));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Perfil Mascota");
        tabLayout.getTabAt(1).setText("Lista Mascotas");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.acerca:

                Intent i = new Intent(this,Contacto.class);
                startActivity(i);

            break;

            case R.id.contacto:

                Intent in = new Intent(this,Contacto.class);
                startActivity(in);

                break;

            case R.id.configurarCuenta:

                Intent in2 = new Intent(this,ConfigurarCuenta.class);
                startActivity(in2);

                break;

            case R.id.recibirNotificaciones:
                obtenerIDUsuario();
                registroToken();
                break;


        }

        return super.onOptionsItemSelected(item);
    }

    public void registroToken() {

        SharedPreferences miCuentaPref = getSharedPreferences("MiCuenta", Context.MODE_PRIVATE);
        SharedPreferences prefe=getSharedPreferences("datos",Context.MODE_PRIVATE);
        String idDispo = miCuentaPref.getString("idDispositivo", null);
        String idUsuInstagram = prefe.getString("idUsuario", null);
        if(idDispo == null){
            //Log.v("Id Dispositivo",idDispo);
            //Log.v("Cuenta", idUsuInstagram);
            idDispo = FirebaseInstanceId.getInstance().getToken();
            //Log.v("Id Dispositivo_reg", idDispo);
            enviaIdDispositivo(idDispo, idUsuInstagram);
        }else{
            //Log.v("Id Dispositivo",idDispo);
            Toast.makeText(MainActivity.this, "Dispositivo ya registrado", Toast.LENGTH_LONG).show();
        }

    }


    private void  enviaIdDispositivo(String id_dispositivo, String id_usuario_instagram){
        //Log.d("id_dispositivo: ",id_dispositivo);
        //Log.d("id_usuario_instagram: ",id_usuario_instagram);

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        EndpointsApi endPoints = restApiAdapter.establecerConexionRestHeroku();
        retrofit2.Call<UsuarioResponse> usuarioResponseCall = endPoints.setUsuarioInstagram(id_dispositivo,id_usuario_instagram);

        usuarioResponseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(retrofit2.Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                UsuarioResponse usuarioResponse = response.body();
                SharedPreferences miCuenta = getSharedPreferences("MiCuenta", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = miCuenta.edit();
                editor.putString("idDispositivo", usuarioResponse.getId());
                editor.commit();
                Toast.makeText(MainActivity.this, "Registro dispositivo exitoso", Toast.LENGTH_LONG).show();
                //Log.d("TOKEN_FIREBASE",usuarioResponse.getToken());
            }

            @Override
            public void onFailure(retrofit2.Call<UsuarioResponse> call, Throwable t) {

                SharedPreferences miCuenta = getSharedPreferences("MiCuenta", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = miCuenta.edit();
                editor.putString("idDispositivo",null);
                editor.commit();
                Toast.makeText(MainActivity.this, "Error en registro dispositivo", Toast.LENGTH_LONG).show();

            }
        });

    }


    public void obtenerIDUsuario() {


        SharedPreferences prefe=getSharedPreferences("datos",Context.MODE_PRIVATE);
        //nombrePerfil=(TextView) findViewById(R.id.tvNombrePerfil);

        //String contacto = nombrePerfil.getText().toString();



        String contacto = prefe.getString("nombreUsuario","");



        RestApiAdapter raa = new RestApiAdapter();
        Gson gsonSearch = raa.construyeGsonDeserializadorIdPorNombre();
        EndpointsApi epa = raa.establecerConexionRestApiInstagram(gsonSearch);

        final Call<MascotaResponse> mascotaResponseCall = epa.search(contacto, ConstantesRestApi.ACCESS_TOKEN);

        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {

                ArrayList<Mascota> mascotas = response.body().getMascotas();

                if (mascotas != null && !mascotas.isEmpty()) {

                    SharedPreferences miCuenta = getSharedPreferences("MiCuenta", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = miCuenta.edit();

                    editor.putString("idUsuario", mascotas.get(0).getId());
                    editor.commit();
                }else{
                    //Snackbar.make(this,"No se encontró el usuario ingresado.",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {

            }
        });


    }




}
