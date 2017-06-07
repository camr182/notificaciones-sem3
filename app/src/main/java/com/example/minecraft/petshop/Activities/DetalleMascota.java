package com.example.minecraft.petshop.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minecraft.petshop.R;
import com.squareup.picasso.Picasso;

public class DetalleMascota extends AppCompatActivity {


    private ImageView fotoDetalle;
    private TextView numLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_mascota);

        Bundle extras = getIntent().getExtras();
        String url = extras.getString("url");
        int likes = extras.getInt("likes");

        fotoDetalle = (ImageView) findViewById(R.id.ivDetalleMascota);
        numLikes = (TextView) findViewById(R.id.tvLikesDetalle);


        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.fondo_imagen)
                .into(fotoDetalle);
        numLikes.setText(String.valueOf(likes));
    }
}
