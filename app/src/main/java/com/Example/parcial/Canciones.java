package com.Example.parcial;

public class Canciones {
    private String Nombre;
    private String Artista;
    private int Duracion;

    public Canciones() {
    }

    public Canciones(String Nombre, String artista, int duracion) {
        this.Nombre = Nombre;
        Artista = artista;
        Duracion = duracion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getArtista() {
        return Artista;
    }

    public void setArtista(String artista) {
        Artista = artista;
    }

    public int getDuracion() {
        return Duracion;
    }

    public void setDuracion(int duracion) {
        Duracion = duracion;
    }
}
