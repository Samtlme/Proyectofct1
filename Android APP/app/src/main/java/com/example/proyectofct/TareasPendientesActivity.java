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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TareasPendientesActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ArrayList<Tareas> listaTareasPendientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tareas_pendientes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tareaspendientes), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ////////
        ListView lv_tareas_pendientes = findViewById(R.id.lv_tareas_pendientes);
        AdapterTareas adaptertareas = new AdapterTareas(getApplicationContext(), listaTareasPendientes);
        lv_tareas_pendientes.setAdapter(adaptertareas);

        try {
            preferenciasHelper helper = new preferenciasHelper(getApplicationContext());
            db = helper.getWritableDatabase();
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(UtilController.getBaseConnectionString(getApplicationContext(),
                            db,
                            "usuarios/tareas/" +
                                    UtilController.getUserIdFromPreferentes(getApplicationContext())))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e("ERROR", "No se pudo recuperar tareas de usuario." + e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String responseData="";

                    try {
                        responseData = response.body().string();
                        JSONArray tareasArray = new JSONArray(responseData);
                        listaTareasPendientes = new ArrayList<>();

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

                            listaTareasPendientes.add(tarea);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AdapterTareas adaptertareas = new AdapterTareas(getApplicationContext(), listaTareasPendientes);
                                lv_tareas_pendientes.setAdapter(adaptertareas);
                                adaptertareas.notifyDataSetChanged();
                            }
                        });


                    }
                    catch (JSONException jsonex){
                        Log.e("KO", "Error de JSON en tareas usuario");
                    }finally{
                        db.close();
                    }

                }
            });


        }catch (Exception e){
            Log.e("KO","Error recuperando las tareas del usuario");
        }finally{
            db.close();
        }

        //eventos

        lv_tareas_pendientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(TareasPendientesActivity.this, TareaDetalleActivity.class);
                intent.putExtra("id_tarea", listaTareasPendientes.get(position).getId_tarea());
                startActivity(intent);

            }
        });


    }
}