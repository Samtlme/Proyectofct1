package com.example.proyectofct.bbdd.entities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.proyectofct.R;
import com.example.proyectofct.bbdd.preferenciasHelper;
import com.example.proyectofct.bbdd.prefsDAO;
import com.example.proyectofct.util.UtilController;

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

        if(tareas.get(position).getEstado().toLowerCase().equals("completada")){
            tituloTarea.setText("(COMPLETADA) " + tareas.get(position).getNombre_tarea());
        }else{
            tituloTarea.setText(tareas.get(position).getNombre_tarea());
        }


        SQLiteDatabase db = null;
        try{
            String datos="";
            preferenciasHelper helper = new preferenciasHelper(contexto);
            db = helper.getWritableDatabase();
            prefsDAO prefsdao = new prefsDAO();
            Preferencias prefs = prefsdao.getPreferences(db,contexto);

            if(tareas.get(position).getPrioridad_tarea().toUpperCase().equals("S")){
                datos = prefs.getS_icon();
            } else if (tareas.get(position).getPrioridad_tarea().toUpperCase().equals("A")) {
                datos = prefs.getA_icon();
            } else{
                datos = prefs.getB_icon();
            }

            byte[] imageBytes = Base64.decode(datos, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes,0, imageBytes.length);
            ImageView iv_icono = elemento.findViewById(R.id.iv_prioridad);
            iv_icono.setImageBitmap(bitmap);

            if(position % 2 == 0){
                ((LinearLayout)(elemento.findViewById(R.id.LL_tareas))).setBackgroundColor(Color.parseColor("#EEEEEE"));
            }

        }catch(Exception e){
            Log.e("KO", "Error asignando icono");
        }finally{
            db.close();
        }

        return elemento;
    }
}
