package com.example.minecraft.petshop.vista.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minecraft.petshop.Adaptadores.MascotaAdapter;
import com.example.minecraft.petshop.model.Mascota;
import com.example.minecraft.petshop.R;
import com.example.minecraft.petshop.presentador.Fragment_listado_mascotasPresenter;
import com.example.minecraft.petshop.restApi.model.JsonKeys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameza on 21/03/2017.
 */

public class Fragment_listado_mascotas extends Fragment {



    private Fragment_listado_mascotasPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_listado_mascotas,container,false);
        SharedPreferences preps = getActivity().getSharedPreferences("datosPersonales", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preps.edit();
        edit.putString(JsonKeys.USER_ID, "5075864862");

        presenter = new Fragment_listado_mascotasPresenter(getContext(),v,getActivity());


        return v;
    }
}
