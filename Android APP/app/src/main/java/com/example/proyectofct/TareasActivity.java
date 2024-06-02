package com.example.proyectofct;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectofct.bbdd.entities.AdapterProyectos;
import com.example.proyectofct.bbdd.entities.AdapterTareas;
import com.example.proyectofct.bbdd.entities.Tareas;
import com.example.proyectofct.bbdd.preferenciasHelper;
import com.example.proyectofct.util.UtilController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TareasActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ArrayList<Tareas> listaTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tareas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.maintareas), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent proyectoIntent = getIntent();
        Integer proyectoId = proyectoIntent.getIntExtra("id_proyecto",-1);

        ListView lv_tareas = findViewById(R.id.lv_tareas);
        AdapterTareas adaptertareas = new AdapterTareas(getApplicationContext(), listaTareas);
        lv_tareas.setAdapter(adaptertareas);

        try {
            preferenciasHelper helper = new preferenciasHelper(getApplicationContext());
            db = helper.getWritableDatabase();
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(UtilController.getBaseConnectionString(getApplicationContext(),db, "tareas/proyecto/"+proyectoId))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e("ERROR", "No se pudo recuperar tareas." + e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String responseData="";

                    try {
                        responseData = response.body().string();
                        JSONArray tareasArray = new JSONArray(responseData);
                        listaTareas = new ArrayList<>();

                        for (int i = 0; i < tareasArray.length();i++){
                            JSONObject tareasDato = tareasArray.getJSONObject(i);

                            Tareas tarea = new Tareas(
                                    tareasDato.getInt("id_tarea"),
                                    null,
                                    null,
                                    tareasDato.getString("nombre_tarea"),
                                    tareasDato.getString("descripcion"),
                                    tareasDato.getString("estado"),
                                    tareasDato.getInt("puntos_tarea"),
                                    tareasDato.getString("prioridad_tarea")
                            );

                            listaTareas.add(tarea);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //esta por duplicado porque me dejo de funcionar por arte de magia entre una ejecucion y otra
                                //ListView lv_tareas = findViewById(R.id.lv_tareas);
                                AdapterTareas adaptertareas = new AdapterTareas(getApplicationContext(), listaTareas);
                                lv_tareas.setAdapter(adaptertareas);
                                adaptertareas.notifyDataSetChanged();
                            }
                        });


                    }
                    catch (JSONException jsonex){
                        Log.e("KO", "Error de JSON en tareas");
                    }finally{
                        if (db != null && db.isOpen()) {
                            try {
                                db.close();
                            } catch (Exception e) {
                                Log.e("KO", "error cerrando la BD");
                            }
                        }
                    }

                }
            });


        }catch (Exception e){
            Log.e("KO","Error recuperando las tareas del proyecto");
        }finally{
            if (db != null && db.isOpen()) {
                try {
                    db.close();
                } catch (Exception e) {
                    Log.e("KO", "error cerrando la BD");
                }
            }
        }

        //eventos

        lv_tareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(TareasActivity.this, TareaDetalleActivity.class);
                intent.putExtra("id_tarea", listaTareas.get(position).getId_tarea());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (db != null && db.isOpen()) {
            try {
                db.close();
            } catch (Exception e) {
                Log.e("KO", "error cerrando la BD");
            }
        }
        super.onDestroy();
    }
}