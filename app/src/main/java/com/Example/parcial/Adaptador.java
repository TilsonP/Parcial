package com.Example.parcial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyViewHolder> {
    private ArrayList<Canciones> Lista;

    public Adaptador(ArrayList<Canciones> list) {
        this.Lista = list;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView nombre;
        TextView artista;
        TextView duracion;

        public MyViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            duracion = itemView.findViewById(R.id.duracion);
            artista = itemView.findViewById(R.id.artista);
        }

        public TextView getArtista() {
            return artista;
        }

        public void setArtista(TextView artista) {
            this.artista = artista;
        }

        public TextView getNombre() {
            return nombre;
        }

        public void setNombre(TextView nombre) {
            this.nombre = nombre;
        }

        public TextView getDuracion() {
            return duracion;
        }

        public void setDuracion(TextView duracion) {
            this.duracion = duracion;
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Canciones cancion = Lista.get(position);
        holder.getNombre().setText(cancion.getNombre());
        holder.getArtista().setText(cancion.getArtista());
        holder.getDuracion().setText(String.valueOf(cancion.getDuracion()));
    }

    @Override
    public Adaptador.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }
}
