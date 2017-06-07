package com.example.minecraft.petshop.Adaptadores;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minecraft.petshop.model.Mascota;
import com.example.minecraft.petshop.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ameza on 21/03/2017.
 */

public class MascotaPerfilAdapter extends RecyclerView.Adapter<MascotaPerfilAdapter.MascotaPerfilViewHolder> {

    private List<Mascota> items;
    Activity activity;

    public MascotaPerfilAdapter(List<Mascota> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }

    public static class MascotaPerfilViewHolder extends RecyclerView.ViewHolder {

        public TextView likes;
        public ImageView foto;

        public MascotaPerfilViewHolder(View itemView) {
            super(itemView);

            foto = (ImageView) itemView.findViewById(R.id.imgFotoPerfilCv);
            likes = (TextView) itemView.findViewById(R.id.tvLikesPerfilCv);

        }

    }




    @Override
    public MascotaPerfilViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_perfil_mascota,parent,false);
        return new MascotaPerfilViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MascotaPerfilViewHolder holder, int position) {

        final Mascota mascota = items.get(position);

        Picasso.with(activity)
                .load(mascota.getUrlFoto())
                .placeholder(R.drawable.fondo_imagen)
                .into(holder.foto);

        holder.likes.setText(String.valueOf(mascota.getLikes()));

        /*
        holder.imagenPerfil.setImageResource(items.get(position).getImagen());
        holder.nombrePerfil.setText(items.get(position).getNombre());
        holder.foto1.setImageResource(items.get(position).getFoto1());
        holder.foto2.setImageResource(items.get(position).getFoto2());
        holder.foto3.setImageResource(items.get(position).getFoto3());
*/
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
