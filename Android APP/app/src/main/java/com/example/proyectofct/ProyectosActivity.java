package com.example.proyectofct;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyectofct.bbdd.entities.AdapterProyectos;
import com.example.proyectofct.bbdd.entities.Preferencias;
import com.example.proyectofct.bbdd.entities.Proyectos;
import com.example.proyectofct.bbdd.preferenciasHelper;
import com.example.proyectofct.bbdd.prefsDAO;
import com.example.proyectofct.util.UtilController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProyectosActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ArrayList<Proyectos> listaProyectos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_proyectos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.proyectos), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lv_proyectos = findViewById(R.id.lv_proyectos);
        AdapterProyectos adapterproyectos = new AdapterProyectos(getApplicationContext(),listaProyectos);
        lv_proyectos.setAdapter(adapterproyectos);

        //recuperamos datos de la API

        try {
            preferenciasHelper helper = new preferenciasHelper(getApplicationContext());
            db = helper.getWritableDatabase();
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(UtilController.getBaseConnectionString(getApplicationContext(),db, "proyectos"))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e("ERROR", "No se pudo recuperar proyectos." + e.toString());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String responseData="";
                    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                    try {
                        responseData = response.body().string();

                        //manejamos JSON y listview data

                        //JSONObject json = new JSONObject(responseData);
                        // JSONArray proyectosArray = json.getJSONArray("proyectos"); // Dejo esto por si necesito recuperar algo especifico
                        JSONArray proyectosArray = new JSONArray(responseData);
                        listaProyectos = new ArrayList<>();

                        for (int i = 0; i < proyectosArray.length(); i++){
                            JSONObject proyectoDato = proyectosArray.getJSONObject(i);
                            Date fechainicio = !proyectoDato.getString("fecha_inicio").split("T")[0].equals("null")?
                                    Date.valueOf(proyectoDato.getString("fecha_inicio").split("T")[0]) :
                                    null;

                            Date fechafin = !proyectoDato.getString("fecha_fin").split("T")[0].equals("null")?
                                    Date.valueOf(proyectoDato.getString("fecha_fin").split("T")[0]) :
                                    null;

                            Proyectos proyectoObjeto = new Proyectos(
                              proyectoDato.getInt("idproyecto"),
                              proyectoDato.getString("nombre_proyecto"),
                              proyectoDato.getString("descripcion"),
                              fechainicio,
                              fechafin
                            );
                            listaProyectos.add(proyectoObjeto);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ListView lv_proyectos = findViewById(R.id.lv_proyectos);
                                AdapterProyectos adapterproyectos = new AdapterProyectos(getApplicationContext(),listaProyectos);
                                lv_proyectos.setAdapter(adapterproyectos);
                                adapterproyectos.notifyDataSetChanged();
                            }
                        });



                    }catch(IllegalArgumentException pe){
                        Log.e("KO", "Error parseo fecha");
                    }
                    catch (JSONException jsonex){
                        Log.e("KO", "Error de JSON");
                    }finally{
                        db.close();
                    }


                }
            });

        }catch (Exception e){
            Log.e("KO", "Error en la petición de proyectos.");
        }

        //resto de cosas

        lv_proyectos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ProyectosActivity.this, TareasActivity.class );
                intent.putExtra("id_proyecto",listaProyectos.get(position).getId_proyecto() );
                startActivity(intent);
            }
        });

        Button btn_test = findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ListView lv_proyectos = findViewById(R.id.lv_proyectos);
                AdapterProyectos adapterproyectos = new AdapterProyectos(getApplicationContext(),listaProyectos);
                lv_proyectos.setAdapter(adapterproyectos);
                adapterproyectos.notifyDataSetChanged();
            }
        });




    }
}