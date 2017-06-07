package com.example.minecraft.petshop.model;

/**
 * Created by minecraft on 12/03/2017.
 */

public class Mascota {

    private String id;
    private String nombreCompleto;
    private String urlFoto;
    private String idImagen;
    private int likes = 0;
    private String urlFotoPerfil;

    public void sumaLike () {
        this.likes = this.likes + 1;
    }

    public Mascota() {
    }

    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    public Mascota(String idImagen){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(String idImagen) {
        this.idImagen = idImagen;
    }
}
