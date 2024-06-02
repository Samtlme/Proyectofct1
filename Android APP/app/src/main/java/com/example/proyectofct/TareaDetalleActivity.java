package com.example.proyectofct;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TareaDetalleActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Tareas tarea = new Tareas();
    ArrayList<Tareas> listaTareasPendientesUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tarea_detalle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tareadetalle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent proyectoIntent = getIntent();
        Integer tareaId = proyectoIntent.getIntExtra("id_tarea", -1);

        TextView tv_tarea_nombre = findViewById(R.id.tvTareaTarea);
        TextView tv_tarea_descripcion = findViewById(R.id.tvTareaDrescripcion);
        TextView tv_tarea_estado = findViewById(R.id.tvTareaEstado);
        TextView tv_tarea_puntos = findViewById(R.id.tvTareaPuntos);
        TextView tv_tarea_prioridad = findViewById(R.id.tvTareaPrioridad);
        Button btn_iniciarCompletarTarea = findViewById(R.id.btn_iniciarCompletarTarea);
        /////
        try {
            preferenciasHelper helper = new preferenciasHelper(getApplicationContext());
            db = helper.getWritableDatabase();
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(UtilController.getBaseConnectionString(getApplicationContext(), db, "tareas/" + tareaId))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e("ERROR", "No se pudo recuperar tarea." + e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String responseData = "";

                    try {
                        responseData = response.body().string();
                        JSONObject tareasDato = new JSONObject(responseData);

                        tarea = new Tareas(
                                tareasDato.getInt("id_tarea"),
                                null,
                                null,
                                tareasDato.getString("nombre_tarea"),
                                tareasDato.getString("descripcion"),
                                tareasDato.getString("estado"),
                                tareasDato.getInt("puntos_tarea"),
                                tareasDato.getString("prioridad_tarea")
                        );

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_tarea_nombre.setText(tarea.getNombre_tarea());
                                tv_tarea_descripcion.setText(tarea.getDescripcion());
                                tv_tarea_estado.setText(tarea.getEstado());
                                tv_tarea_puntos.setText(tarea.getPuntos_tarea().toString());

                                switch (tarea.getPrioridad_tarea().toUpperCase()) {
                                    case "S":
                                        tv_tarea_prioridad.setText("Urgente!");

                                        break;

                                    case "A":
                                        tv_tarea_prioridad.setText("Alta");
                                        break;

                                    case "B":
                                        tv_tarea_prioridad.setText("Baja");
                                        break;

                                    default:
                                        tv_tarea_prioridad.setText("No especificada");
                                }

                            }
                        });

                        //comprobamos si el usuario tiene la tarea

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
                                String responseData = "";

                                try {
                                    responseData = response.body().string();
                                    JSONArray tareasArray = new JSONArray(responseData);
                                    listaTareasPendientesUsuario = new ArrayList<>();

                                    for (int i = 0; i < tareasArray.length(); i++) {
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

                                        listaTareasPendientesUsuario.add(tarea);
                                    }

                                    btn_iniciarCompletarTarea.setText("INICIAR TAREA");

                                    for (Tareas item : listaTareasPendientesUsuario) {
                                        if(item.getId_tarea() == tarea.getId_tarea()){
                                            btn_iniciarCompletarTarea.setText("COMPLETAR TAREA");
                                        }
                                    }

                                } catch (JSONException jsonex) {
                                    Log.e("KO", "Error de JSON en tarea detalle.");
                                }
                            }
                        });
                    } catch (JSONException jsonex) {
                        Log.e("KO", "Error de JSON en tarea detalle.");
                    }
                }
            });
        } catch (Exception e) {
            Log.e("KO", "Error FATAL en tarea detalle.");
        }

        //Boton


        btn_iniciarCompletarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tarea.getEstado().toLowerCase().equals("completada")){
                    Toast.makeText(TareaDetalleActivity.this, "La tarea ya se encuentra completada!", Toast.LENGTH_SHORT).show();
                }else {

                    if (btn_iniciarCompletarTarea.getText().toString().toUpperCase().contains("COMPLET")) {
                        /////
                        try {
                            preferenciasHelper helper = new preferenciasHelper(getApplicationContext());
                            db = helper.getWritableDatabase();
                            OkHttpClient client = new OkHttpClient();

                            Request request = new Request.Builder()
                                    .url(UtilController.getBaseConnectionString(getApplicationContext(),
                                            db,
                                            "usuarios/tareas/completar/" +
                                                    tarea.getId_tarea() + "/" +
                                                    UtilController.getUserIdFromPreferentes(getApplicationContext())))
                                    .build();

                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    Log.e("ERROR", "No se pudo completar tarea de usuario." + e.toString());
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                    try {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(TareaDetalleActivity.this, "Tarea completada", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } catch (Exception ex) {
                                        Log.e("KO", "Error completando tarea.");
                                    }

                                }
                            });
                        } catch (Exception e) {
                            Log.e("KO", "Error recuperando la tarea del usuario");
                        }finally{
                            if (db != null && db.isOpen()) {
                                try {
                                    db.close();
                                } catch (Exception e) {
                                    Log.e("KO", "error cerrando la BD");
                                }
                            }
                        }
                        /////

                    } else if (btn_iniciarCompletarTarea.getText().toString().toUpperCase().contains("INIC")) {
                        /////
                        try {
                            preferenciasHelper helper = new preferenciasHelper(getApplicationContext());
                            db = helper.getWritableDatabase();
                            OkHttpClient client = new OkHttpClient();

                            Request request = new Request.Builder()
                                    .url(UtilController.getBaseConnectionString(getApplicationContext(),
                                            db,
                                            "usuarios/tareas/iniciar/" +
                                                    tarea.getId_tarea() + "/" +
                                                    UtilController.getUserIdFromPreferentes(getApplicationContext())))
                                    .build();

                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    Log.e("ERROR", "No se pudo iniciar tarea de usuario." + e.toString());
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                    try {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(TareaDetalleActivity.this, "Tarea iniciada", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } catch (Exception ex) {
                                        Log.e("KO", "Error iniciando tarea.");
                                    }

                                }
                            });
                        } catch (Exception e) {
                            Log.e("KO", "Error inicianto las tarea del usuario");
                        }finally{
                            if (db != null && db.isOpen()) {
                                try {
                                    db.close();
                                } catch (Exception e) {
                                    Log.e("KO", "error cerrando la BD");
                                }
                            }
                        }
                        /////
                    }
                }
                finish();
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