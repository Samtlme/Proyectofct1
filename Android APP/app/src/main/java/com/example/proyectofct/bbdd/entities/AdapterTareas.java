package com.example.proyectofct.bbdd.entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.proyectofct.R;

import java.util.ArrayList;

public class AdapterTareas extends BaseAdapter {

    private ArrayList<Tareas> tareas;
    private Context contexto;

    public AdapterTareas(Context contexto, ArrayList<Tareas> listatareas){
        super();
        this.contexto = contexto;
        this.tareas = listatareas;
    }

    @Override
    public int getCount() {
        return tareas != null? tareas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return tareas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(contexto);
        View elemento = inflater.inflate(R.layout.tarea_item, parent, false);
        TextView tituloTarea = elemento.findViewById(R.id.tv_tareas_inner);
        tituloTarea.setText(tareas.get(position).getNombre_tarea());

        //TODO flags prioridad


        return elemento;
    }
}
