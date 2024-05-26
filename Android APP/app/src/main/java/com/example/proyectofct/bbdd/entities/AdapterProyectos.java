package com.example.proyectofct.bbdd.entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.proyectofct.R;

import java.util.ArrayList;

public class AdapterProyectos extends BaseAdapter {

    private ArrayList<Proyectos> proyectos;
    private Context contexto;

    public AdapterProyectos(Context context, ArrayList<Proyectos> listaproyectos){
        super();
        this.contexto = context;
        this.proyectos = listaproyectos;
    }

    @Override
    public int getCount() {
        return proyectos != null? proyectos.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return proyectos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View elemento = inflater.inflate(R.layout.proyecto_item, parent,false);
        TextView tituloProyecto = elemento.findViewById(R.id.tv_proyectos_inner);
        tituloProyecto.setText(proyectos.get(position).getNombre_proyecto());

        //TODO descripcion y más datos, si te da tiempo

        return elemento;
    }
}
